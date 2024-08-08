package com.hifx.syncadapterexample.ui.presentation.image

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImageLoadUtils(val context: Context) {

    fun loadImages(
        callBack: (imageList: List<ImageModel>) -> Unit,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.DISPLAY_NAME,
                MediaStore.Images.ImageColumns._ID,
            )
            val result = context.contentResolver.query(
                uri,
                projection,
                null,
                null,
                null,
            )
            val list = arrayListOf<ImageModel>()
            result?.let { cursor ->
                val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                val nameColumn = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
                while (cursor.moveToNext()) {
                    val uri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        cursor.getLong(idColumn),
                    )
                    val name = cursor.getString(nameColumn)
                    list.add(
                        ImageModel(name, uri),
                    )
                }
            }
            result?.close()
            withContext(Dispatchers.Main) {
                callBack.invoke(list)
            }
        }
    }
}

data class ImageModel(
    val name: String,
    val uri: Uri,
)
