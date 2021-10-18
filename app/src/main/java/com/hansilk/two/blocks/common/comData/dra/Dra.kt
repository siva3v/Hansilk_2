package com.hansilk.two.blocks.common.comData.dra

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dra")
class Dra() {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @ColumnInfo(name = "aa")
    var aa: String? = null

    @ColumnInfo(name = "ac")
    var ac: String? = null

    @ColumnInfo(name = "ba")
    var ba: String? = null

    @ColumnInfo(name = "bb")
    var bb: String? = null

    @ColumnInfo(name = "bc")
    var bc: String? = null

    @ColumnInfo(name = "bd")
    var bd: String? = null

    @ColumnInfo(name = "be")
    var be: String? = null

}