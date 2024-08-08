package com.hifx.syncadapterexample.ui.presentation.notes.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDAO {

    @Insert(onConflict = REPLACE)
    suspend fun insertNote(noteEntity: NoteEntity)

    @Query("SELECT * FROM note_table")
    fun getNoteListAsFlow(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note_table")
    suspend fun getNoteList(): List<NoteEntity>

    @Query("DELETE FROM note_table")
    suspend fun deleteMasterData()

    @Query("DELETE FROM note_table WHERE id = :id")
    suspend fun deleteNoteWithId(id: String)
}