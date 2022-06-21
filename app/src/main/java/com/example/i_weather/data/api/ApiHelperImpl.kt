package com.example.i_weather.data.api

import com.example.i_weather.data.model.ResultCurrentWeather
import com.example.i_weather.data.model.ResultForecast
import com.example.i_weather.data.model.ResultGeocoding
import io.reactivex.Observable
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override fun getWeather(lon: Double, lat: Double): Observable<ResultCurrentWeather> {
        return apiService.getWeather(lon, lat)
    }

    override fun getCity(city: String): Observable<MutableList<ResultGeocoding>> {
        return apiService.getCity(city)
    }

    override fun getForecast(lon: Double, lat: Double): Observable<ResultForecast> {
        return apiService.getForecast(lon, lat)
    }
}