package com.hansilk.two.support.utils.listUtils

import java.util.*

class ListUtils {
    companion object {

        fun ArrayListToString(ar: ArrayList<*>): String {
            return ar.toString().replace("[", "").replace("]", "")
        }

        fun ArrayListToStringWith___(ar: ArrayList<String>): String {
            var result = ""
            for (item in ar) result += "___$item"
            return result.substring(3)
        }

        fun StringToArrayListWith___(string: String): ArrayList<String> {
            val result = ArrayList<String>()
            val list: List<String> = string.split("___")
            for (item in list) if (item.isNotEmpty()) result.add(item)
            return result
        }

        fun ArrayListToStringWithNoSpaces(ar: ArrayList<*>): String? {
            return ar.toString().replace("[", "").replace("]", "").replace(" ", "")
        }



        fun ListToStringWith___(list: ArrayList<String>): String {
            var result = ""
            for (item in list) result += "___$item"
            return result.substring(3)
        }

        fun StringToListWith___(string: String): List<String> {

            return string.split("___")
        }

    }
}