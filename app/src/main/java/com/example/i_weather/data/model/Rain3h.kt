package com.example.i_weather.data.model

import com.google.gson.annotations.SerializedName

data class Rain3h(
    @SerializedName("3h") var rain3h: Double? = null
)