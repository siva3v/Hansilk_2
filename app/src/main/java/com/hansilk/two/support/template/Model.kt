package com.hansilk.two.support.template

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "models")
class Model {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

}