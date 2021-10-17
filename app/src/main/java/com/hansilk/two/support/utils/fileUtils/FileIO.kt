package com.hansilk.two.support.utils.fileUtils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import androidx.core.app.ActivityCompat
import com.hansilk.two.support.utils.permissions.PermissionCodes
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception

class FileIO {
    companion object {

        fun saveCollectionImageReturnPath(context: Context, bitmap: Bitmap, filename: Long): File {
            val folder: File? = context.getExternalFilesDir("Hansilk/Two")
            val file = File(folder, "$filename.jpg")

            return saveBitmapToFile(bitmap, file)
        }

        @Throws(IOException::class)
        fun saveBitmapToFile(bitmap: Bitmap, file: File): File {
            val bytes = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, bytes)
            //val file: File = makeExtFileHansilkTwoJpg(context, filename)
            val fo = FileOutputStream(file)
            fo.write(bytes.toByteArray())
            fo.close()
            return file
        }


        private fun makeExtFileHansilkTwoJpg(context: Context, filename: String): File {

            val folder: File? = context.getExternalFilesDir("Hansilk/Two")

            return File(folder, "$filename.jpg")
        }

        @Throws(IOException::class)
        fun makeExtFileHansilkTwo(context: Context, filename: String): File {
            val file = File(Environment.getExternalStorageDirectory().toString() + "/Hansilk/Two/")

            if (!file.exists()) file.mkdirs()
            val f = File(file, "$filename.jpg")
            f.createNewFile()
            return f
        }

        fun savePublicly(activity: Activity, string: String) {
            // Requesting Permission to access External Storage
            ActivityCompat.requestPermissions(
                activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PermissionCodes.EXTERNAL_STORAGE_PERMISSION_CODE
            )

            // getExternalStoragePublicDirectory() represents root of external storage, we are using DOWNLOADS
            // We can use following directories: MUSIC, PODCASTS, ALARMS, RINGTONES, NOTIFICATIONS, PICTURES, MOVIES
            val folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

            // Storing the data in file with name as geeksData.txt
            val file = File(folder, "file.txt")
            writeTextData(file, string)
        }

        fun savePrivately(context: Context, string: String) {

            // Creating folder with name GeekForGeeks
            val folder: File? = context.getExternalFilesDir("Hansilk")

            // Creating file with name file.txt
            val file = File(folder, "file.txt")
            writeTextData(file, string)
        }

        // writeTextData() method save the data into the file in byte format
        // It also toast a message "Done/filepath_where_the_file_is_saved"
        private fun writeTextData(file: File, data: String) {
            var fileOutputStream: FileOutputStream? = null
            try {
                fileOutputStream = FileOutputStream(file)
                fileOutputStream.write(data.toByteArray())
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }

    }

}