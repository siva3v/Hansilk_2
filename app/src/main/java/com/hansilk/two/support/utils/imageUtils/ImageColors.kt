package com.hansilk.two.support.utils.imageUtils

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception
import java.util.ArrayList
import kotlin.math.sqrt

class ImageColors {
    companion object {

        fun getPossibleColorsFromBitmap (btp: Bitmap): ArrayList<String> {

            val newBitmap = Bitmap.createScaledBitmap(btp, 12, 12, true)
            val gcar = ArrayList<Int>()

            for (x in 1..5) {
                for (y in 1..5) {
                    val cz = newBitmap.getPixel(x * 2, y * 2)
                    gcar.add(cz)
                }
            }
            val colorsList = ArrayList<String>()
            for (gc in gcar) {
                try {
                    val fc = getNearestColor(gc).split("_").toTypedArray()
                    if (!colorsList.contains(fc[0])) colorsList.add(fc[0])
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            return colorsList
        }

        @Throws(JSONException::class)
        fun getNearestColor (gc: Int): String {
            val jc = JSONObject()
            jc.put("311_White", Color.WHITE)
            jc.put("312_Black", Color.BLACK)
            jc.put("318_Grey", Color.GRAY)
            jc.put("314_Yellow", Color.YELLOW)
            jc.put("321_Lime", getColorFromInt("#00FF00"))
            jc.put("313_Green", Color.GREEN)
            jc.put("322_Cyan", Color.CYAN)
            jc.put("317_Blue", Color.BLUE)
            jc.put("315_Purple", getColorFromInt("#800080"))
            jc.put("323_Pink", getColorFromInt("#FF00FF"))
            jc.put("460_Magenta", Color.MAGENTA)
            jc.put("320_Red", Color.RED)
            jc.put("316_Orange", getColorFromInt("#FFA500"))
            jc.put("319_Silver", getColorFromInt("#C0C0C0"))
            var fc = ""
            var ds = 99999.0
            val iter = jc.keys()
            while (iter.hasNext()) {
                val ky = iter.next()
                val db = getDistanceBetweenColors3D(gc, jc.getInt(ky))
                if (db < ds) {
                    ds = db
                    fc = ky
                }
            }
            return fc
        }

        fun getDistanceBetweenColors2D(ia: Int, ib: Int): Int {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val a = Color.valueOf(ia)
                val b = Color.valueOf(ib)
                (Math.abs(a.red() - b.red()) + Math.abs(a.green() - b.green()) + Math.abs(a.blue() - b.blue())).toInt()
            } else {
                9999
            }
        }

        fun getDistanceBetweenColors3D(ia: Int, ib: Int): Double {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val a = Color.valueOf(ia)
                val b = Color.valueOf(ib)
                sqrt(
                    Math.pow(
                        (a.red() - b.red()).toDouble(),
                        2.0
                    ) + Math.pow(
                        (a.green() - b.green()).toDouble(),
                        2.0
                    ) + Math.pow((a.blue() - b.blue()).toDouble(), 2.0)
                )
            } else {
                9999.0
            }
        }

        fun getColorFromInt(hx: String?): Int {
            return try {
                Color.parseColor(hx)
            } catch (e: Exception) {
                Color.BLACK
            }
        }


    }
}