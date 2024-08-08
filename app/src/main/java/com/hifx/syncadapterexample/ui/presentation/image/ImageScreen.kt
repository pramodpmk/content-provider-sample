package com.hifx.syncadapterexample.ui.presentation.image

import android.Manifest
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.hifx.syncadapterexample.ui.components.RequestPermission

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ImageScreen(
    navHostController: NavHostController,
) {
    val context = LocalContext.current
    var imageLoadUtils by remember {
        mutableStateOf(ImageLoadUtils(context))
    }
    var hasPermission by remember { mutableStateOf(false) }
    var imageList by remember {
        mutableStateOf(arrayListOf<ImageModel>())
    }
    var permissionState =
        rememberPermissionState(permission = Manifest.permission.READ_EXTERNAL_STORAGE)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        if (permissionState.status != PermissionStatus.Granted) {
            RequestPermission(
                permission = Manifest.permission.READ_EXTERNAL_STORAGE,
                rationale = "We need access to your storage to load images.",
            ) { isGranted ->
                hasPermission = isGranted
            }
        } else {
            // Your composable that needs the permission
            Column {
                Text(
                    text = "Load Images",
                    modifier = Modifier
                        .clickable {
                            imageLoadUtils.loadImages {
                                println("Image loaded from provider : $it")
                                val tempList = arrayListOf<ImageModel>()
                                tempList.addAll(imageList)
                                val resList = it.mapTo(ArrayList()) { img ->
                                    img
                                }
                                tempList.addAll(resList)
                                imageList = tempList
                            }
                        }
                        .padding(20.dp),
                )
                Spacer(modifier = Modifier.height(50.dp))
                if (imageList.size > 0) {
                    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                        imageList.forEach { image ->
                            item {
                                Column {
                                    Text(
                                        text = image.name,
                                        modifier = Modifier.padding(50.dp),
                                    )
                                    AsyncImage(
                                        model = image.uri,
                                        contentDescription = image.name,
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
