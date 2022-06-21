package com.example.i_weather.data.api

import com.example.i_weather.data.model.ResultCurrentWeather
import com.example.i_weather.data.model.ResultForecast
import com.example.i_weather.data.model.ResultGeocoding
import io.reactivex.Observable

interface ApiHelper {
    fun getWeather(lon: Double, lat: Double): Observable<ResultCurrentWeather>
    fun getCity(city: String): Observable<MutableList<ResultGeocoding>>
    fun getForecast(lon: Double, lat: Double): Observable<ResultForecast>
}