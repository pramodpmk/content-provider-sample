package com.hifx.syncadapterexample.ui.presentation.notes.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        NoteEntity::class,
    ],
    version = 1,
)

abstract class NoteDataBase : RoomDatabase() {

    abstract fun newsDao(): NoteDAO
}
