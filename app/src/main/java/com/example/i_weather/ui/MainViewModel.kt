package com.example.i_weather.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.i_weather.base.BaseViewModel
import com.example.i_weather.data.model.Coord
import com.example.i_weather.data.model.ResultCurrentWeather
import com.example.i_weather.data.model.ResultForecast
import com.example.i_weather.data.model.ResultGeocoding
import com.example.i_weather.data.respository.ApiRepository
import com.example.i_weather.util.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {
    @Inject
    lateinit var networkHelper: NetworkHelper

    @Inject
    lateinit var apiRepository: ApiRepository

    val defaultCoord = MutableLiveData<Coord>()

    val resultCurrentWeather = MutableLiveData<ResultCurrentWeather>()
    val resultCity = MutableLiveData<MutableList<ResultGeocoding>>()
    val resultForecast = MutableLiveData<ResultForecast>()
    val currentCity = MutableLiveData<String>()
    val currentCoord = MutableLiveData<Coord>()

    val city = MutableLiveData<String>()
    val isFahrenheit = MutableLiveData<Boolean>()

    fun getWeather(lon: Double, lat: Double, success: () -> Unit, fail: (message: String) -> Unit) {
        if (networkHelper.isNetworkConnected()) {
            addCompositeDisposable(
                apiRepository.getWeather(lon, lat)
                    .subscribeWith(object : DisposableObserver<ResultCurrentWeather>() {
                        override fun onNext(value: ResultCurrentWeather?) {
                            success()
                            resultCurrentWeather.value = value!!
                            Log.d("getWeather", "onNext: $value")
                        }

                        override fun onError(e: Throwable?) {
                            e?.message?.let { fail(it) }
                            e?.printStackTrace()
                        }

                        override fun onComplete() {
                        }
                    })
            )
        } else fail("Check your network connection")
    }

    fun getCity(city: String, success: () -> Unit, fail: (message: String) -> Unit) {
        if (networkHelper.isNetworkConnected()) {
            addCompositeDisposable(
                apiRepository.getCity(city)
                    .subscribeWith(object : DisposableObserver<MutableList<ResultGeocoding>>() {
                        override fun onNext(value: MutableList<ResultGeocoding>?) {
                            success()
                            resultCity.value = value!!
                            Log.d("getCity", "onNext: $value")
                        }

                        override fun onError(e: Throwable?) {
                            e?.message?.let { fail(it) }
                            e?.printStackTrace()
                        }

                        override fun onComplete() {
                        }
                    })
            )
        } else fail("Check your network connection")
    }

    fun getForecast(
        lon: Double,
        lat: Double,
        success: () -> Unit,
        fail: (message: String) -> Unit
    ) {
        if (networkHelper.isNetworkConnected()) {
            addCompositeDisposable(
                apiRepository.getForecast(lon, lat)
                    .subscribeWith(object : DisposableObserver<ResultForecast>() {
                        override fun onNext(value: ResultForecast?) {
                            success()
                            resultForecast.value = value!!
                            Log.d("getForecast", "onNext: $value")
                        }

                        override fun onError(e: Throwable?) {
                            e?.message?.let { fail(it) }
                            e?.printStackTrace()
                        }

                        override fun onComplete() {
                        }
                    })
            )
        } else fail("Check your network connection")
    }
}