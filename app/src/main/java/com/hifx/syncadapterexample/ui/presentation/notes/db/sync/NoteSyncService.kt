package com.hifx.syncadapterexample.ui.presentation.notes.db.sync

import android.app.Service
import android.content.Intent
import android.os.IBinder

class NoteSyncService : Service() {

    private lateinit var syncAdapter: NoteSyncAdapter
    private val syncAdapterLock = Any()

    override fun onCreate() {
        super.onCreate()
        synchronized(syncAdapterLock) {
            if (!::syncAdapter.isInitialized) {
                syncAdapter = NoteSyncAdapter(applicationContext, true)
            }
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return syncAdapter.syncAdapterBinder
    }
}