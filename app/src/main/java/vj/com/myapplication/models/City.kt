package vj.com.myapplication.models

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

open class City(

	@field:SerializedName("country")
	var country: String,

	@field:SerializedName("coord")
	var coord: Coord,

	@field:SerializedName("name")
	var name: String,

	@PrimaryKey
	@field:SerializedName("id")
	var id: Int
) : RealmObject(), Serializable {
    constructor() : this("", Coord(-1.0, -1.0), "", -1)
}
