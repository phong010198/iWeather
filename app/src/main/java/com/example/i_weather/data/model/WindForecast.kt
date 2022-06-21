package com.example.i_weather.data.model

import com.google.gson.annotations.SerializedName

data class WindForecast(
    @SerializedName("speed") var speed: Double? = null,
    @SerializedName("deg") var deg: Int? = null,
    @SerializedName("gust") var gust: Double? = null
)