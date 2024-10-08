package com.hifx.syncadapterexample.ui.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hifx.syncadapterexample.ui.presentation.notes.db.DatabaseContentObserver
import com.hifx.syncadapterexample.ui.presentation.notes.db.NoteDbHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject() constructor(
    val noteDbHelper: NoteDbHelper,
    val databaseContentObserver: DatabaseContentObserver,
) : ViewModel() {

    private val _noteListState = MutableStateFlow<List<NoteItemModel>?>(null)
    val noteListState = _noteListState.asStateFlow()
    private val _addNoteState = MutableStateFlow<AddNoteUiState>(AddNoteUiState.Idle)
    val addNoteState = _addNoteState.asStateFlow()

    init {
        databaseContentObserver.register()
    }

    fun observeDataChange() {
        viewModelScope.launch {
            databaseContentObserver.dataChangeFlow.collectLatest { state ->
                println("NoteViewModel - Data state change collected - $state")
            }
        }
    }

    fun loadNoteList() {
        viewModelScope.launch {
            noteDbHelper.loadNotes {
                _noteListState.value = it
            }
        }
    }

    fun addNote(text: String) {
        viewModelScope.launch {
            _addNoteState.value = AddNoteUiState.Loading
            noteDbHelper.saveNote(
                title = text,
                date = System.currentTimeMillis(),
                callBack = {
                    if (it) {
                        _addNoteState.value = AddNoteUiState.Success("Save success")
                    } else {
                        _addNoteState.value = AddNoteUiState.Error("Save failed")
                    }
                },
            )
        }
    }

    override fun onCleared() {
        databaseContentObserver.unregister()
        super.onCleared()
    }
}
