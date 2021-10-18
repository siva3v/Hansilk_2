package com.hansilk.two.blocks.uploads.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "uploads")
class Upload() {

	@PrimaryKey(autoGenerate = true)
	var id: Int? = null

	@ColumnInfo(name = "state")
	var state: Int? = null

	@ColumnInfo(name = "ab")
	var ab: Long? = null

	@ColumnInfo(name = "path")
	var path: String? = null

	@ColumnInfo(name = "bc")
	var bc: Int? = null

}
