package com.hifx.syncadapterexample.ui.presentation.contact

import android.Manifest
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddContactScreen(
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
        rememberPermissionState(permission = Manifest.permission.WRITE_CONTACTS)
    var contactName by remember {
        mutableStateOf("")
    }
    var contactNumber by remember {
        mutableStateOf("")
    }
    var message by remember {
        mutableStateOf("")
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        if (message.isNotEmpty()) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
        if (permissionState.status != PermissionStatus.Granted) {
            RequestPermission(
                permission = Manifest.permission.READ_CONTACTS,
                rationale = "We need access to your contacts to save contact.",
            ) { isGranted ->
                hasPermission = isGranted
            }
        } else {
            // Your composable that needs the permission
            Column {
                Row {
                    TextField(
                        value = contactName,
                        onValueChange = {
                            contactName = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                    )
                }
                Spacer(modifier = Modifier.height(50.dp))
                TextField(
                    value = contactNumber,
                    onValueChange = {
                        contactNumber = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                )
                Spacer(modifier = Modifier.size(20.dp))
                Button(onClick = {
                    contactLoadUtils.saveContacts(
                        contactName,
                        contactNumber,
                    ) {
                        message = if (it) {
                            "Success"
                        } else {
                            "Fail"
                        }
                    }
                }) {
                    Text(text = "Save")
                }
            }
        }
    }
}
