package com.example.i_weather.data.model.current_weather

import com.google.gson.annotations.SerializedName

data class WindCurrentWeather(
    @SerializedName("speed") var speed: Double? = null,
    @SerializedName("deg") var deg: Int? = null
)
