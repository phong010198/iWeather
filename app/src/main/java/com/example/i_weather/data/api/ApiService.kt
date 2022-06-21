package com.example.i_weather.data.api

import com.example.i_weather.data.model.ResultCurrentWeather
import com.example.i_weather.data.model.ResultForecast
import com.example.i_weather.data.model.ResultGeocoding
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("data/2.5/weather?appid=8a162d98c9520cde549e914e6fd84ec6&units=metric")
    fun getWeather(
        @Query("lon") lon: Double,
        @Query("lat") lat: Double
    ): Observable<ResultCurrentWeather>

    @GET("geo/1.0/direct?appid=8a162d98c9520cde549e914e6fd84ec6&limit=7")
    fun getCity(@Query("q") city: String): Observable<MutableList<ResultGeocoding>>

    @GET("data/2.5/forecast?appid=8a162d98c9520cde549e914e6fd84ec6&units=metric&cnt=9")
    fun getForecast(
        @Query("lon") lon: Double,
        @Query("lat") lat: Double
    ): Observable<ResultForecast>
}