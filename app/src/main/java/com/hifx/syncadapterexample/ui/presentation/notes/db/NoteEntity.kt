package com.hifx.syncadapterexample.ui.presentation.notes.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var title: String? = null,
    var date: Long? = null
)
