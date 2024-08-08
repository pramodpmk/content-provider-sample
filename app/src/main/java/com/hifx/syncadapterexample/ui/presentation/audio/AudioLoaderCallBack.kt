package com.hifx.syncadapterexample.ui.presentation.audio

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader

class AudioLoaderCallBack(
    private val context: Context,
    private val onAudioLoaded: (Cursor?) -> Unit,
) : LoaderManager.LoaderCallbacks<Cursor> {

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
        )
        return CursorLoader(context, uri, projection, null, null, null)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        onAudioLoaded.invoke(data)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        onAudioLoaded.invoke(null)
    }
}
