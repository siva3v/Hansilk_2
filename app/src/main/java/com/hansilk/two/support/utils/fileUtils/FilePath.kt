package com.hansilk.two.support.utils.fileUtils

import android.os.Build
import android.provider.DocumentsContract
import android.os.Environment
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import java.lang.Exception

object FilePath {
    fun getPath(context: Context, uri: Uri): String? {
        var uri = uri
        var selection: String? = null
        var selectionArgs: Array<String>? = null
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(
                context.applicationContext,
                uri
            )
        ) {
            when {
                isExternalStorageDocument(uri) -> {
                    val split = DocumentsContract.getDocumentId(uri).split(":").toTypedArray()
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }
                isDownloadsDocument(uri) -> {
                    uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        java.lang.Long.valueOf(DocumentsContract.getDocumentId(uri)).toLong()
                    )
                }
                isMediaDocument(uri) -> {
                    val split2 = DocumentsContract.getDocumentId(uri).split(":").toTypedArray()
                    val type = split2[0]
                    when (type) {
                        "image" -> {
                            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        }
                        "video" -> {
                            uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                        }
                        "audio" -> {
                            uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                        }
                    }
                    selection = "_id=?"
                    selectionArgs = arrayOf(split2[1])
                }
            }
        }
        return when {
            "content".equals(uri.scheme, ignoreCase = true) -> {
                try {
                    val cursor = context.contentResolver.query(
                        uri,
                        arrayOf("_data"),
                        selection,
                        selectionArgs,
                        null as String?
                    )
                    val column_index = cursor!!.getColumnIndexOrThrow("_data")
                    if (cursor.moveToFirst()) {
                        cursor.getString(column_index)
                    } else null
                } catch (e: Exception) {
                    null
                }
            }
            "file".equals(uri.scheme, ignoreCase = true) -> {
                uri.path
            }
            else -> {
                null
            }
        }
    }

    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

}