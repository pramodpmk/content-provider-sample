package com.hifx.syncadapterexample.ui.presentation.notes.db

import android.content.ContentValues
import android.content.Context
import com.hifx.syncadapterexample.ui.presentation.notes.NoteItemModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteDbHelper @Inject constructor(
    val context: Context,
) {

    fun loadNotes(
        callBack: (imageList: List<NoteItemModel>) -> Unit,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val uri = NoteContentProvider.noteAllUri
            println("uri : $uri")
            val result = context.contentResolver.query(
                uri,
                null,
                null,
                null,
                null,
            )
            val list = arrayListOf<NoteItemModel>()
            result?.let { cursor ->
                val idColumn = cursor.getColumnIndex("id")
                val titleColumn = cursor.getColumnIndex("title")
                val dateColumn = cursor.getColumnIndex("date")
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(idColumn)
                    val title = cursor.getString(titleColumn)
                    val date = cursor.getLong(dateColumn)
                    list.add(
                        NoteItemModel(
                            id,
                            title,
                            date.toString(),
                        ),
                    )
                }
            }
            result?.close()
            withContext(Dispatchers.Main) {
                callBack.invoke(list)
            }
        }
    }

    fun loadNoteById(
        noteId: Int,
        callBack: (imageList: List<NoteItemModel>) -> Unit,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val uri = NoteContentProvider.noteWithIdUri(noteId)
            println("uri : $uri")
            val result = context.contentResolver.query(
                uri,
                null,
                null,
                null,
                null,
            )
            val list = arrayListOf<NoteItemModel>()
            result?.let { cursor ->
                val idColumn = cursor.getColumnIndex("id")
                val titleColumn = cursor.getColumnIndex("title")
                val dateColumn = cursor.getColumnIndex("date")
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(idColumn)
                    val title = cursor.getString(titleColumn)
                    val date = cursor.getLong(dateColumn)
                    list.add(
                        NoteItemModel(
                            id,
                            title,
                            date.toString(),
                        ),
                    )
                }
            }
            result?.close()
            withContext(Dispatchers.Main) {
                callBack.invoke(list)
            }
        }
    }

    fun saveNote(
        title: String,
        date: Long,
        callBack: (insertStatus: Boolean) -> Unit,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val contentResolver = context.contentResolver
            val contentValues = ContentValues().apply {
                put("title", title)
                put("date", date)
            }
            val result = contentResolver.insert(
                NoteContentProvider.noteAllUri,
                contentValues,
            )
            println("Inserted URI $result")
            withContext(Dispatchers.Main) {
                callBack.invoke(true)
            }
        }
    }
}
