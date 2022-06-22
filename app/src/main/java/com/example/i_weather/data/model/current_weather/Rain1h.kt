package com.example.i_weather.data.model.current_weather

import com.google.gson.annotations.SerializedName

data class Rain1h(
    @SerializedName("1h") var rain1h: Double? = null
)