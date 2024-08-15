package com.hifx.syncadapterexample.ui.presentation.notes.db.sync

import android.accounts.Account
import android.content.AbstractThreadedSyncAdapter
import android.content.ContentProviderClient
import android.content.Context
import android.content.SyncResult
import android.os.Bundle
import androidx.room.Room
import com.hifx.syncadapterexample.ui.presentation.notes.db.NoteDAO
import com.hifx.syncadapterexample.ui.presentation.notes.db.NoteDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NoteSyncAdapter(
    context: Context,
    autoInitialize: Boolean,
) : AbstractThreadedSyncAdapter(context, autoInitialize) {

    private var noteDao: NoteDAO? = null

    init {
        noteDao = context.applicationContext?.let {
            Room.databaseBuilder(
                it,
                NoteDataBase::class.java,
                "note_database",
            ).fallbackToDestructiveMigration().build()
        }?.noteDao()
    }

    override fun onPerformSync(
        account: Account?,
        extras: Bundle?,
        authority: String?,
        provider: ContentProviderClient?,
        syncResult: SyncResult?,
    ) {
        println("NoteSyncAdapter>>>onPerformSync")
        CoroutineScope(Dispatchers.IO).launch {
            delay(10000)
            println("NoteSyncAdapter>>>onPerformSync sync complete")
        }
    }
}
