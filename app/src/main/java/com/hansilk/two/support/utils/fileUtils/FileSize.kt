package com.hansilk.two.support.utils.fileUtils

import java.io.File

class FileSize {
    companion object{

        fun fileSizeInKB(f: File): Long {
            // Get length of file in bytes
            val fileSizeInBytes = f.length()
            // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
            val fileSizeInKB = fileSizeInBytes / 1024
            //  Convert the KB to MegaBytes (1 MB = 1024 KBytes)
            val fileSizeInMB = fileSizeInKB / 1024
            return fileSizeInKB
        }


    }
}