package com.hansilk.two.support.utils.imageUtils

import android.content.res.Resources
import android.graphics.*
import android.net.Uri
import kotlin.math.max
import android.content.Context

import android.graphics.BitmapFactory
import com.hansilk.two.support.utils.fileUtils.FilePath
import android.graphics.Bitmap
import com.hansilk.two.support.utils.fileUtils.FileBitmap
import java.lang.Exception
import java.lang.NullPointerException

class ImageEdit {
    companion object {

        fun buildImageGridFromUriArrayForProductGroup (imageList: ArrayList<String>): Bitmap {

            var nn = 2
            if (imageList.size < 10) nn = 2
            var n = 0
            val h = 420
            val w = 360
            val xx = nn
            val yy = 3
            val gp = 12
            val tw = yy * w + gp * (yy - 1)
            val th = xx * h + gp * (xx - 1)
            val btp = Bitmap.createBitmap(tw, th, Bitmap.Config.ARGB_8888)

            return try {
                val canvas = Canvas(btp)
                val pnta = Paint()
                pnta.color = Color.WHITE
                pnta.style = Paint.Style.FILL
                canvas.drawPaint(pnta)
                for (x in 0..xx) for (y in 0..yy) {
                    if (n >= imageList.size) n = 0

                    val path = imageList[n]
                    if (path.isNotEmpty()) {
                        val bitmap = FileBitmap.getBitmapFromPath(path)
                        scaleAndCenterCropImage(bitmap, h, w)?.let {
                            canvas.drawBitmap(
                                it,
                                (y * (w + gp)).toFloat(),
                                (x * (h + gp)).toFloat(),
                                null as Paint?
                            )
                        }
                    }

                    n++
                }
                btp
            } catch (notFoundException: Resources.NotFoundException) {
                notFoundException.printStackTrace()
                btp
            }
        }

        private fun scaleAndCenterCropImage (source: Bitmap, newHeight: Int, newWidth: Int): Bitmap? {

            val sourceWidth = source.width
            val sourceHeight = source.height
            val scale = max(
                newWidth.toFloat() / sourceWidth.toFloat(),
                newHeight.toFloat() / sourceHeight.toFloat()
            )

            val scaledWidth = sourceWidth.toFloat() * scale
            val scaledHeight = sourceHeight.toFloat() * scale
            val left = (newWidth.toFloat() - scaledWidth) / 2.0f
            val top = (newHeight.toFloat() - scaledHeight) / 2.0f
            val targetRect = RectF(left, top, left + scaledWidth, top + scaledHeight)

            val dest = Bitmap.createBitmap(newWidth, newHeight, source.config)
            val canvas = Canvas(dest)
            canvas.drawBitmap(source, null, targetRect, null)

            return dest
        }

    }
}