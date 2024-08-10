package com.hifx.syncadapterexample.ui.presentation.notes

sealed class AddNoteUiState {
    object Idle : AddNoteUiState()
    object Loading : AddNoteUiState()
    data class Success(val message: String) : AddNoteUiState()
    data class Error(val message: String) : AddNoteUiState()
}
