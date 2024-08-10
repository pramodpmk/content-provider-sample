package com.hifx.syncadapterexample.ui.presentation.notes.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDAO {

    @Insert
    fun insertNote(noteEntity: NoteEntity): Long

    @Query("SELECT * FROM note_table")
    fun getNoteListAsFlow(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note_table")
    fun getNoteList(): List<NoteEntity>

    @Query("SELECT * FROM note_table WHERE id = :id")
    fun getNoteItem(id: Int): NoteEntity

    @Query("DELETE FROM note_table")
    fun deleteMasterData()

    @Query("DELETE FROM note_table WHERE id = :id")
    fun deleteNoteWithId(id: Int)

    @Update
    fun updateNote(noteEntity: NoteEntity)
}