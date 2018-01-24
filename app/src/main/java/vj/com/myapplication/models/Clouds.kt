package vj.com.myapplication.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Clouds(

	@field:SerializedName("all")
	val all: Int? = null
) : Serializable