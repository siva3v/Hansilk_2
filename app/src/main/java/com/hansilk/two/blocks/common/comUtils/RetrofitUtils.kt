package com.hansilk.two.blocks.common.comUtils

import com.hansilk.two.support.utils.listUtils.ListUtils
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList
import java.util.concurrent.locks.Condition

class RetrofitUtils {
    companion object {


        fun getInsertQueryFromJSONObj(jj: JSONObject, tableName: String): String {

            val cl = ArrayList<String>()
            val vl = ArrayList<String>()
            val iterator = jj.keys()
            while (iterator.hasNext()) {
                val ky = iterator.next()
                if (ky.length == 2) {
                    try {
                        cl.add(ky)
                        vl.add("'" + jj.getString(ky).replace("'", "\\'") + "'")
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }

            return "INSERT INTO $tableName (${ListUtils.ArrayListToString(cl)}) " +
                    "VALUES (${ListUtils.ArrayListToString(vl)})"
        }

        fun getUpdateQueryFromTableRows(tableName: String, update: String, condition: String ): String {

            return "UPDATE `$tableName` SET $update WHERE $condition"
        }

    }
}