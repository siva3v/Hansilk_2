package com.hansilk.two.support.utils.imageUtils

import android.content.Context
import com.hansilk.two.support.utils.fileUtils.FileSize
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.destination
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import java.io.File

class ImageCompress {
    companion object{

        suspend fun compressImageForRhaSm(context: Context, originalFile: File): Pair<File, File> {

            var resultMid = File(context.filesDir, originalFile.name)

            val originalFileSize = FileSize.fileSizeInKB(originalFile)
            if (originalFileSize > 300) {
                val targetQuality: Int = when {
                    originalFileSize < 400 -> 85
                    originalFileSize < 500 -> 80
                    originalFileSize < 600 -> 75
                    originalFileSize < 800 -> 70
                    originalFileSize < 1000 -> 65
                    originalFileSize < 1300 -> 60
                    originalFileSize < 1600 -> 55
                    else -> 50
                }
                resultMid = Compressor.compress(context, originalFile) {
                    resolution(720, 1280)
                    quality(targetQuality)
                    destination(resultMid)
                }
            } else {
                originalFile.copyTo(resultMid, true)
            }

            val resultSmall = Compressor.compress(context, resultMid) {
                resolution(480, 720)
                quality(10)
            }

            return Pair(resultMid, resultSmall)
        }

        suspend fun compressImageMid(context: Context, originalFile: File): File {

            val originalFileSize = FileSize.fileSizeInKB(originalFile)

            val targetQuality: Int = when {
                originalFileSize < 300 -> return originalFile
                originalFileSize < 400 -> 85
                originalFileSize < 500 -> 80
                originalFileSize < 600 -> 75
                originalFileSize < 800 -> 70
                originalFileSize < 1000 -> 65
                originalFileSize < 1300 -> 60
                originalFileSize < 1600 -> 55
                else -> 50
            }

            return Compressor.compress(context, originalFile) {
                resolution(720, 1280)
                quality(targetQuality)
            }
        }

        suspend fun compressImageSmall(context: Context, originalFile: File): File {

            return Compressor.compress(context, originalFile) {
                resolution(480, 720)
                quality(10)
            }
        }

    }
}