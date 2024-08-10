package com.hifx.syncadapterexample.ui.presentation.notes.db

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import androidx.room.Room

class NoteContentProvider() : ContentProvider() {

    companion object {
        const val AUTHORITY = "com.promode.note.provider"
        val noteAllUri: Uri = Uri.parse("content://$AUTHORITY/notes")
        const val CODE_NOTES_ALL = 1
        const val CODE_NOTES_WITH_ID = 2
        fun noteWithIdUri(id: Int): Uri = Uri.parse("content://$AUTHORITY/notes/$id")
        val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {

            /*
             * Set the code 1 for entire table query
             */
            addURI(NoteContentProvider.AUTHORITY, "notes", CODE_NOTES_ALL)

            /*
             * Sets the code for a single row to 2. In this case, the # wildcard is
             */
            addURI(NoteContentProvider.AUTHORITY, "notes/#", CODE_NOTES_WITH_ID)
        }
    }

    private var dataBase: NoteDataBase? = null
    private var noteDao: NoteDAO? = null

    override fun onCreate(): Boolean {
        dataBase = context?.applicationContext?.let {
            Room.databaseBuilder(
                it,
                NoteDataBase::class.java,
                "note_database",
            ).fallbackToDestructiveMigration().build()
        }
        noteDao = dataBase?.noteDao()
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?,
    ): Cursor? {
        val cursor = when (sUriMatcher.match(uri)) {
            CODE_NOTES_ALL -> {
                val notes = noteDao?.getNoteList()
                val temp = MatrixCursor(arrayOf("id", "title", "date"))
                notes?.forEach {
                    temp.addRow(arrayOf(it.id, it.title, it.date))
                }
                temp
            }
            CODE_NOTES_WITH_ID -> {
                val id = ContentUris.parseId(uri).toInt()
                val notes = noteDao?.getNoteItem(id)
                val temp = MatrixCursor(arrayOf("id", "title", "date"))
                temp.addRow(arrayOf(notes?.id, notes?.title, notes?.date))
                temp
            }

            else -> {
                null
            }
        }
        cursor?.setNotificationUri(context?.contentResolver, uri)
        return cursor
    }

    override fun getType(uri: Uri): String? = "vnd.android.cursor.dir/vnd.com.promode.note"

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        values?.let {
            val note = NoteEntity(
                title = it.getAsString("title"),
                date = it.getAsLong("date"),
            )
            val result = noteDao?.insertNote(note)
            context?.contentResolver?.notifyChange(uri, null)
            return Uri.withAppendedPath(noteAllUri, result.toString())
        }
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val id = uri.lastPathSegment?.toIntOrNull() ?: return 0
        noteDao?.deleteNoteWithId(id)
        context?.contentResolver?.notifyChange(uri, null)
        return 1
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?,
    ): Int {
        values?.let {
            val id = uri.lastPathSegment?.toIntOrNull() ?: return 0
            val note = NoteEntity(
                id = id,
                title = it.getAsString("title"),
                date = it.getAsLong("date"),
            )
            noteDao?.updateNote(note)
            context?.contentResolver?.notifyChange(uri, null)
            return 1
        }
        return 0
    }
}
