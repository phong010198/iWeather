package com.example.i_weather.data.api

import com.example.i_weather.data.model.current_weather.ResultCurrentWeather
import com.example.i_weather.data.model.forecast.ResultForecast
import com.example.i_weather.data.model.geo_coding.ResultGeocoding
import io.reactivex.Observable

interface ApiHelper {
    fun getWeather(lon: Double, lat: Double): Observable<ResultCurrentWeather>
    fun getCity(city: String): Observable<MutableList<ResultGeocoding>>
    fun getForecast(lon: Double, lat: Double): Observable<ResultForecast>
}