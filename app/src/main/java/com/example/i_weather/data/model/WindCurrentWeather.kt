package com.example.i_weather.data.model

import com.google.gson.annotations.SerializedName

data class WindCurrentWeather(
    @SerializedName("speed") var speed: Double? = null,
    @SerializedName("deg") var deg: Int? = null
)
