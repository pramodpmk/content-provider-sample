package com.hifx.syncadapterexample.ui.presentation.notes

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun NoteScreen(navHostController: NavHostController) {
    val viewModel = hiltViewModel<NoteViewModel>()
    val noteList = viewModel.noteListState.collectAsState()
    var showAddNote by remember {
        mutableStateOf(false)
    }
    val addNoteState = viewModel.addNoteState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.loadNoteList()
    }
    LaunchedEffect(key1 = addNoteState.value) {
        when (addNoteState.value) {
            is AddNoteUiState.Success -> {
                showAddNote = false
            }

            is AddNoteUiState.Error -> {
                showAddNote = false
            }

            is AddNoteUiState.Loading -> {
            }

            else -> {
                // Do nothing
            }
        }
    }

    Surface {
        Column {
            Text(text = "Notes List", modifier = Modifier.padding(20.dp))
            LazyColumn {
                if ((noteList.value?.size ?: 0) > 0) {
                    noteList.value?.forEach { item ->
                        item {
                            NoteListItem(
                                noteItem = item,
                                onclick = {
                                },
                            )
                        }
                    }
                }
            }
        }
        Box(Modifier.fillMaxWidth()) {
            FloatingActionButton(
                onClick = {
                    // Add note
                    showAddNote = true
                },
                modifier = Modifier.align(Alignment.BottomEnd),
            ) {
                Image(imageVector = Icons.Rounded.AddCircle, contentDescription = "add")
            }
        }
        if (showAddNote) {
            AddNoteScreen(
                navHostController = navHostController,
                onClick = {
                    viewModel.addNote(it)
                },
                onDismiss = {
                    showAddNote = false
                },
            )
        }
    }
}

@Composable
fun NoteListItem(
    noteItem: NoteItemModel,
    onclick: (noteItem: NoteItemModel) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .clickable {
                onclick.invoke(noteItem)
            },
    ) {
        Column {
            Text(
                text = noteItem.title,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = noteItem.date,
                modifier = Modifier.padding(bottom = 16.dp),
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}
