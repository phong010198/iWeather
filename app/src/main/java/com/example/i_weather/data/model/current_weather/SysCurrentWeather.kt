package com.example.i_weather.data.model.current_weather

import com.google.gson.annotations.SerializedName

data class SysCurrentWeather(
    @SerializedName("type") var type: Int? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("sunrise") var sunrise: Int? = null,
    @SerializedName("sunset") var sunset: Int? = null
)
