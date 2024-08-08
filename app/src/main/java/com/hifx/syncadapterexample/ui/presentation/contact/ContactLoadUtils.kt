package com.hifx.syncadapterexample.ui.presentation.contact

import android.content.ContentProviderOperation
import android.content.Context
import android.provider.ContactsContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactLoadUtils(val context: Context) {

    fun loadContacts(
        callBack: (imageList: List<ContactModel>) -> Unit,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            val projection = arrayOf(
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
            )
            val result = context.contentResolver.query(
                uri,
                projection,
                null,
                null,
                null,
            )
            val list = arrayListOf<ContactModel>()
            result?.let { cursor ->
                val nameColumn =
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                val numberColumn =
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                while (cursor.moveToNext()) {
//                    val uri = ContentUris.withAppendedId(
//                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                        cursor.getLong(idColumn),
//                    )
                    val name = cursor.getString(nameColumn)
                    val number = cursor.getString(numberColumn)
                    list.add(
                        ContactModel(name, number),
                    )
                }
            }
            result?.close()
            withContext(Dispatchers.Main) {
                callBack.invoke(list)
            }
        }
    }

    fun saveContacts(
        contactName: String,
        contactNumber: String,
        callBack: (insertStatus: Boolean) -> Unit,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val contentResolver = context.contentResolver
            val operations = ArrayList<ContentProviderOperation>()
            val rawContactInsertIndex = operations.size
            // Insert an empty raw contact
            operations.add(
                ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                    .build(),
            )
            // Insert display name
            operations.add(
                ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(
                        ContactsContract.Data.RAW_CONTACT_ID,
                        rawContactInsertIndex,
                    )
                    .withValue(
                        ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE,
                    )
                    .withValue(
                        ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                        contactName,
                    )
                    .build(),
            )

            // Insert phone number
            operations.add(
                ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(
                        ContactsContract.Data.RAW_CONTACT_ID,
                        rawContactInsertIndex,
                    )
                    .withValue(
                        ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
                    )
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contactNumber)
                    .withValue(
                        ContactsContract.CommonDataKinds.Phone.TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,
                    )
                    .build(),
            )
            val result = try {
                contentResolver.applyBatch(ContactsContract.AUTHORITY, operations)
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
            withContext(Dispatchers.Main) {
                callBack.invoke(result)
            }
        }
    }
}

data class ContactModel(
    val name: String,
    val number: String,
)
