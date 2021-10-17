package com.hansilk.two.support.utils.uidUtils

class UidUtils {
    companion object {

        private var LAST_UID_MILLIES = 0L

        fun getUidMillies () : Long{
            var uid = System.currentTimeMillis()

            if (uid <= LAST_UID_MILLIES) uid = LAST_UID_MILLIES+1

            LAST_UID_MILLIES = uid
            return uid
        }

    }
}