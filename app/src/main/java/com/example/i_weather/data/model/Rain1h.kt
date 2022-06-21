package com.example.i_weather.data.model

import com.google.gson.annotations.SerializedName

data class Rain1h(
    @SerializedName("1h") var rain1h: Double? = null
)