package com.hansilk.two.support.utils.fileUtils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri

class FileBitmap {
    companion object {

        fun getBitmapFromUri(context: Context, uri: Uri): Bitmap {
            val path = FilePath.getPath(context, uri)
            return BitmapFactory.decodeFile(path)
        }

        fun getBitmapFromPath(path: String): Bitmap {
            return BitmapFactory.decodeFile(path)
        }

    }
}