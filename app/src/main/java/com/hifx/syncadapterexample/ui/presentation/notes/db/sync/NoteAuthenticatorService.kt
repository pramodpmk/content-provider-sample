package com.hifx.syncadapterexample.ui.presentation.notes.db.sync

import android.app.Service
import android.content.Intent
import android.os.IBinder

class NoteAuthenticatorService : Service() {

    private lateinit var authenticator: NoteAuthenticator

    override fun onCreate() {
        authenticator = NoteAuthenticator(this)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return authenticator.iBinder
    }
}
