package vj.com.myapplication.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Main(

	@field:SerializedName("temp")
	val temp: Double? = null,

	@field:SerializedName("temp_min")
	val tempMin: Double? = null,

	@field:SerializedName("humidity")
	val humidity: Double? = null,

	@field:SerializedName("pressure")
	val pressure: Double? = null,

	@field:SerializedName("temp_max")
	val tempMax: Double? = null
) : Serializable