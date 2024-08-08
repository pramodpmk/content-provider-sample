package com.hifx.syncadapterexample.ui.presentation.notes

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject() constructor() : ViewModel() {

    private val _noteListState = MutableStateFlow<List<NoteItemModel>?>(null)
    val noteListState = _noteListState.asStateFlow()

    fun loadNoteList() {
        _noteListState.value = arrayListOf(
            NoteItemModel(1, "Note one", "21 May 2024"),
            NoteItemModel(2, "Note two", "21 May 2024"),
            NoteItemModel(3, "Note three", "21 May 2024"),
        )
    }

    fun addNote(text: String) {
    }
}
