package vj.com.myapplication.models

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import java.io.Serializable

open class Coord(

	@field:SerializedName("lon")
	var lon: Double? = null,

	@field:SerializedName("lat")
	var lat: Double? = null
) : RealmObject(), Serializable {
	constructor() : this(-1.0, -1.0)
}