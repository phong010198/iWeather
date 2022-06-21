package com.example.i_weather.data.respository

import com.example.i_weather.data.api.ApiHelper
import com.example.i_weather.data.model.ResultCurrentWeather
import com.example.i_weather.data.model.ResultForecast
import com.example.i_weather.data.model.ResultGeocoding
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiHelper: ApiHelper) {
    fun getWeather(lon: Double, lat: Double): Observable<ResultCurrentWeather> =
        apiHelper.getWeather(lon, lat).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getCity(city: String): Observable<MutableList<ResultGeocoding>> =
        apiHelper.getCity(city).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getForecast(lon: Double, lat: Double): Observable<ResultForecast> =
        apiHelper.getForecast(lon, lat).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}