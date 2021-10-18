package com.hansilk.two.blocks.uploads.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
class Product() : Parcelable {

	@PrimaryKey(autoGenerate = true)
	var id: Int? = null

	@ColumnInfo(name = "state")
	var state: Int? = null

	@ColumnInfo(name = "path")
	var path: String? = null

	@ColumnInfo(name = "colors")
	var colors: String? = null

	@ColumnInfo(name = "de")
	var de: String? = null

	@ColumnInfo(name = "df")
	var df: String? = null

	@ColumnInfo(name = "tw")
	var tw: String? = null

	@ColumnInfo(name = "ty")
	var ty: String? = null

	@ColumnInfo(name = "ia")
	var ia: String? = null

	@ColumnInfo(name = "ib")
	var ib: String? = null

	@ColumnInfo(name = "aa")
	var aa: String? = null

	@ColumnInfo(name = "ab")
	var ab: String? = null

	@ColumnInfo(name = "ac")
	var ac: String? = null

	@ColumnInfo(name = "iy")
	var iy: String? = null

	@ColumnInfo(name = "rb")
	var rb: String? = null

	@ColumnInfo(name = "fa")
	var fa: String? = null

	@ColumnInfo(name = "fb")
	var fb: String? = null

	@ColumnInfo(name = "fc")
	var fc: String? = null

	@ColumnInfo(name = "fd")
	var fd: String? = null

	@ColumnInfo(name = "ba")
	var ba: String? = null

	@ColumnInfo(name = "fe")
	var fe: String? = null

	@ColumnInfo(name = "bb")
	var bb: String? = null

	@ColumnInfo(name = "ff")
	var ff: String? = null

	@ColumnInfo(name = "bc")
	var bc: String? = null

	@ColumnInfo(name = "fg")
	var fg: String? = null

	@ColumnInfo(name = "bd")
	var bd: String? = null

	@ColumnInfo(name = "fh")
	var fh: String? = null

	@ColumnInfo(name = "bf")
	var bf: String? = null

	@ColumnInfo(name = "bi")
	var bi: String? = null

	@ColumnInfo(name = "bj")
	var bj: String? = null

	@ColumnInfo(name = "bk")
	var bk: String? = null

	@ColumnInfo(name = "bl")
	var bl: String? = null

	@ColumnInfo(name = "bm")
	var bm: String? = null

	@ColumnInfo(name = "bn")
	var bn: String? = null

	@ColumnInfo(name = "bo")
	var bo: String? = null

	@ColumnInfo(name = "bp")
	var bp: String? = null

	@ColumnInfo(name = "ca")
	var ca: String? = null

	@ColumnInfo(name = "cb")
	var cb: String? = null

	@ColumnInfo(name = "cc")
	var cc: String? = null

	@ColumnInfo(name = "cd")
	var cd: String? = null

	@ColumnInfo(name = "ce")
	var ce: String? = null

	@ColumnInfo(name = "cf")
	var cf: String? = null

	@ColumnInfo(name = "cg")
	var cg: String? = null

	@ColumnInfo(name = "ch")
	var ch: String? = null

	@ColumnInfo(name = "ci")
	var ci: String? = null

	@ColumnInfo(name = "cj")
	var cj: String? = null

	@ColumnInfo(name = "ck")
	var ck: String? = null

	@ColumnInfo(name = "cl")
	var cl: String? = null

	@ColumnInfo(name = "cm")
	var cm: String? = null

	@ColumnInfo(name = "cn")
	var cn: String? = null

	@ColumnInfo(name = "co")
	var co: String? = null

	@ColumnInfo(name = "cp")
	var cp: String? = null

	@ColumnInfo(name = "cq")
	var cq: String? = null

	@ColumnInfo(name = "cr")
	var cr: String? = null

	@ColumnInfo(name = "cs")
	var cs: String? = null

	@ColumnInfo(name = "ct")
	var ct: String? = null

	@ColumnInfo(name = "cu")
	var cu: String? = null

	@ColumnInfo(name = "cv")
	var cv: String? = null

	@ColumnInfo(name = "cx")
	var cx: String? = null

	constructor(parcel: Parcel) : this() {
		id = parcel.readValue(Int::class.java.classLoader) as? Int
		path = parcel.readString()
		colors = parcel.readString()
		de = parcel.readString()
		df = parcel.readString()
		tw = parcel.readString()
		ty = parcel.readString()
		ia = parcel.readString()
		ib = parcel.readString()
		aa = parcel.readString()
		ab = parcel.readString()
		ac = parcel.readString()
		iy = parcel.readString()
		rb = parcel.readString()
		fa = parcel.readString()
		fb = parcel.readString()
		fc = parcel.readString()
		fd = parcel.readString()
		ba = parcel.readString()
		fe = parcel.readString()
		bb = parcel.readString()
		ff = parcel.readString()
		bc = parcel.readString()
		fg = parcel.readString()
		bd = parcel.readString()
		fh = parcel.readString()
		bf = parcel.readString()
		bi = parcel.readString()
		bj = parcel.readString()
		bk = parcel.readString()
		bl = parcel.readString()
		bm = parcel.readString()
		bn = parcel.readString()
		bo = parcel.readString()
		bp = parcel.readString()
		ca = parcel.readString()
		cb = parcel.readString()
		cc = parcel.readString()
		cd = parcel.readString()
		ce = parcel.readString()
		cf = parcel.readString()
		cg = parcel.readString()
		ch = parcel.readString()
		ci = parcel.readString()
		cj = parcel.readString()
		ck = parcel.readString()
		cl = parcel.readString()
		cm = parcel.readString()
		cn = parcel.readString()
		co = parcel.readString()
		cp = parcel.readString()
		cq = parcel.readString()
		cr = parcel.readString()
		cs = parcel.readString()
		ct = parcel.readString()
		cu = parcel.readString()
		cv = parcel.readString()
		cx = parcel.readString()
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeValue(id)
		parcel.writeString(path)
		parcel.writeString(colors)
		parcel.writeString(de)
		parcel.writeString(df)
		parcel.writeString(tw)
		parcel.writeString(ty)
		parcel.writeString(ia)
		parcel.writeString(ib)
		parcel.writeString(aa)
		parcel.writeString(ab)
		parcel.writeString(ac)
		parcel.writeString(iy)
		parcel.writeString(rb)
		parcel.writeString(fa)
		parcel.writeString(fb)
		parcel.writeString(fc)
		parcel.writeString(fd)
		parcel.writeString(ba)
		parcel.writeString(fe)
		parcel.writeString(bb)
		parcel.writeString(ff)
		parcel.writeString(bc)
		parcel.writeString(fg)
		parcel.writeString(bd)
		parcel.writeString(fh)
		parcel.writeString(bf)
		parcel.writeString(bi)
		parcel.writeString(bj)
		parcel.writeString(bk)
		parcel.writeString(bl)
		parcel.writeString(bm)
		parcel.writeString(bn)
		parcel.writeString(bo)
		parcel.writeString(bp)
		parcel.writeString(ca)
		parcel.writeString(cb)
		parcel.writeString(cc)
		parcel.writeString(cd)
		parcel.writeString(ce)
		parcel.writeString(cf)
		parcel.writeString(cg)
		parcel.writeString(ch)
		parcel.writeString(ci)
		parcel.writeString(cj)
		parcel.writeString(ck)
		parcel.writeString(cl)
		parcel.writeString(cm)
		parcel.writeString(cn)
		parcel.writeString(co)
		parcel.writeString(cp)
		parcel.writeString(cq)
		parcel.writeString(cr)
		parcel.writeString(cs)
		parcel.writeString(ct)
		parcel.writeString(cu)
		parcel.writeString(cv)
		parcel.writeString(cx)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<Product> {
		override fun createFromParcel(parcel: Parcel): Product {
			return Product(parcel)
		}

		override fun newArray(size: Int): Array<Product?> {
			return arrayOfNulls(size)
		}
	}

}
