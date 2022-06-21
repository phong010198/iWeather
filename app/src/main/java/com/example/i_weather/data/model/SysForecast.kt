package com.example.i_weather.data.model

import com.google.gson.annotations.SerializedName

data class SysForecast(
    @SerializedName("pod") var pod: String? = null
)