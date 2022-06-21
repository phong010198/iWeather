package com.example.i_weather.data.model

import com.google.gson.annotations.SerializedName

data class ResultGeocoding(
    @SerializedName("name") var name: String? = null,
    @SerializedName("local_names") var localNames: LocalNames? = LocalNames(),
    @SerializedName("lat") var lat: Double? = null,
    @SerializedName("lon") var lon: Double? = null,
    @SerializedName("country") var country: String? = null
)