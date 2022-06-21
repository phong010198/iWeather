package com.example.i_weather.data.model

import com.google.gson.annotations.SerializedName

data class ResultForecast(
    @SerializedName("cod") var cod: String? = null,
    @SerializedName("message") var message: Int? = null,
    @SerializedName("cnt") var cnt: Int? = null,
    @SerializedName("list") var list: MutableList<Forecast> = mutableListOf(),
    @SerializedName("city") var city: City? = City()
)