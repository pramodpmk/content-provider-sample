package com.hifx.syncadapterexample.ui.presentation.notes.db

import android.accounts.Account
import android.accounts.AccountManager
import android.app.Activity
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.UriMatcher
import android.database.ContentObserver
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class DatabaseContentObserver @Inject constructor(val context: Context) {

    private lateinit var noteContentObserver: NoteContentObserver
    private lateinit var account: Account

    init {
        account = createSyncAccount()
    }

    val AUTHORITY = "com.promode.note.provider"
    val noteAllUri: Uri = Uri.parse("content://$AUTHORITY/notes")
    val CODE_NOTES_ALL = 1
    val CODE_NOTES_WITH_ID = 2
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

    inner class NoteContentObserver(handler: Handler) : ContentObserver(handler) {
        override fun onChange(selfChange: Boolean, uri: Uri?) {
            super.onChange(selfChange, uri)
            uri?.let {
                println("onChange uri - $uri")
                val match = sUriMatcher.match(uri)
                when (match) {
                    CODE_NOTES_ALL -> {
                        println("Content changed for table note")
                        _dataChangeFlow.value = "Changed uri - $uri table"
                    }
                    CODE_NOTES_WITH_ID -> {
                        val id = ContentUris.parseId(uri)
                        // Handle the change, you now have the specific ID of the changed row
                        println("Content changed for row ID: $id")
                        ContentResolver.requestSync(account, "com.promode.note.provider", Bundle())
                        _dataChangeFlow.value = "Changed uri - $uri id - $id"
                    }
                    else -> {
                        println("Unsupported Uri - $uri")
                    }
                }
            }
        }
    }

    private fun createSyncAccount(): Account {
        val accountType = "com.hifx.syncadapterexample"
        val accountName = "DefaultAccount"
        val account = Account(accountName, accountType)
        val accountManager = context.getSystemService(Activity.ACCOUNT_SERVICE) as AccountManager
        if (accountManager.addAccountExplicitly(account, null, null)) {
            // Account created
        } else {
            // Account already exists or error occurred
        }
        return account
    }

    private val _dataChangeFlow = MutableStateFlow<String>("")
    val dataChangeFlow = _dataChangeFlow.asStateFlow()

    fun register() {
        noteContentObserver = NoteContentObserver(Handler(Looper.getMainLooper()))
        context.contentResolver.registerContentObserver(
            Uri.parse("content://com.promode.note.provider/notes"),
            true,
            noteContentObserver,
        )
    }

    fun unregister() {
        context.contentResolver.unregisterContentObserver(noteContentObserver)
    }
}
