package com.hifx.syncadapterexample.ui.presentation.contact

import android.Manifest
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.hifx.syncadapterexample.ui.components.RequestPermission
import com.hifx.syncadapterexample.ui.navigation.Screen

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ContactScreen(
    navHostController: NavHostController,
) {
    val context = LocalContext.current
    var contactLoadUtils by remember {
        mutableStateOf(ContactLoadUtils(context))
    }
    var hasPermission by remember { mutableStateOf(false) }
    var contactList by remember {
        mutableStateOf(arrayListOf<ContactModel>())
    }
    var permissionState =
        rememberPermissionState(permission = Manifest.permission.READ_CONTACTS)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        if (permissionState.status != PermissionStatus.Granted) {
            RequestPermission(
                permission = Manifest.permission.READ_CONTACTS,
                rationale = "We need access to your storage to load images.",
            ) { isGranted ->
                hasPermission = isGranted
            }
        } else {
            // Your composable that needs the permission
            Column {
                Row {
                    Text(
                        text = "Load Contacts",
                        modifier = Modifier
                            .clickable {
                                contactLoadUtils.loadContacts {
                                    println("Image loaded from provider : $it")
                                    contactList = it.mapTo(ArrayList()) { img ->
                                        img
                                    }
                                }
                            }
                            .padding(20.dp),
                    )
                    Text(
                        text = "Update Contacts",
                        modifier = Modifier
                            .clickable {
                                contactLoadUtils.loadContacts {
                                    println("Image loaded from provider : $it")
                                    contactList = it.mapTo(ArrayList()) { img ->
                                        img
                                    }
                                }
                            }
                            .padding(20.dp),
                    )
                    Text(
                        text = "Save Contacts",
                        modifier = Modifier
                            .clickable {
                                navHostController.navigate(Screen.AddContact.route)
                            }
                            .padding(20.dp),
                    )
                }
                Spacer(modifier = Modifier.height(50.dp))
                if (contactList.size > 0) {
                    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                        contactList.forEach { contact ->
                            item {
                                Box(modifier = Modifier.padding(20.dp)) {
                                    Column {
                                        Text(
                                            text = contact.name,
                                            modifier = Modifier.padding(8.dp),
                                        )
                                        Text(
                                            text = contact.number,
                                            modifier = Modifier.padding(8.dp),
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
