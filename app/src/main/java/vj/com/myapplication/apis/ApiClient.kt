package com.vj.mykotlinapp.apis

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import vj.com.myapplication.apis.ApiInterface
import vj.com.myapplication.apis.HeaderInterceptor

/**
 *
 * Created by VJ on 1/19/2018.
 */

class ApiClient {
    companion object {
        val BASE_URL = "http://api.openweathermap.org/"
        var retrofit: Retrofit? = null

        fun create(): ApiInterface {
            if (retrofit == null) {

                val httpClient = OkHttpClient.Builder()
                httpClient.addInterceptor(HeaderInterceptor())

                retrofit = Retrofit.Builder()
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .client(httpClient.build())
                        .build()
            }
            return retrofit!!.create<ApiInterface>(ApiInterface::class.java)
        }
    }
}