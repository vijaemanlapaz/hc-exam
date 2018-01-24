package vj.com.myapplication.apis

import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import vj.com.myapplication.models.Weather

/**
 *
 * Created by VJ on 1/19/2018.
 */
interface ApiInterface {
//    @Headers("Content-Type: application/json")
    @GET("data/2.5/weather")
    fun getWeather(@Query("id") cityId: Int?): Observable<Weather>
    @GET("data/2.5/find")
    fun searchCity(@Query("q") query: String?, @Query("type") type: String = "like"): Observable<JsonObject>
}