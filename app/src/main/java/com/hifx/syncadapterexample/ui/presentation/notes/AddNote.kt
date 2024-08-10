package com.hifx.syncadapterexample.ui.presentation.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
    navHostController: NavHostController,
    onClick: (title: String) -> Unit,
    onDismiss: () -> Unit,
) {
    var titleText by remember {
        mutableStateOf("")
    }
    ModalBottomSheet(
        onDismissRequest = {
            onDismiss.invoke()
        },
    ) {
        Column {
            Row {
                TextField(
                    value = titleText,
                    onValueChange = {
                        titleText = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
            Button(onClick = {
                onClick.invoke(titleText)
            }) {
                Text(text = "Save")
            }
        }
    }
}
