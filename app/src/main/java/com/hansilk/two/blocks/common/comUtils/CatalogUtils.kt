package com.hansilk.two.blocks.common.comUtils

class CatalogUtils {
    companion object {


        fun getReoCa(cp: Int): Int {
            var ca = 0
            when {
                cp < 500 -> ca = 266
                cp in 500..999 -> ca = 272
                cp in 1000..1499 -> ca = 273
                cp in 1500..1999 -> ca = 274
                cp in 2000..2999 -> ca = 275
                cp in 3000..4999 -> ca = 276
                cp in 5000..9999 -> ca = 278
                cp > 10000 -> ca = 279
            }
            return ca
        }


    }
}