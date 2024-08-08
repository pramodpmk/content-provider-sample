package com.hifx.syncadapterexample.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermission(
    permission: String,
    rationale: String = "",
    onPermissionResult: (Boolean) -> Unit,
) {
    val permissionState = rememberPermissionState(permission)

    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
    }

    when (permissionState.status) {
        is PermissionStatus.Granted -> {
            onPermissionResult(true)
        }

        is PermissionStatus.Denied -> {
            if ((permissionState.status as PermissionStatus.Denied).shouldShowRationale) {
                // Show rationale and request permission again
                AlertDialog(
                    onDismissRequest = { /* TODO */ },
                    title = { Text(text = "Permission Request") },
                    text = { Text(text = rationale) },
                    confirmButton = {
                        Button(onClick = { permissionState.launchPermissionRequest() }) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { onPermissionResult(false) }) {
                            Text("Cancel")
                        }
                    },
                )
            } else {
                onPermissionResult(false)
            }
        }
    }
}
