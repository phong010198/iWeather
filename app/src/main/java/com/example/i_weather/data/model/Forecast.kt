package com.example.i_weather.data.model

import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("dt") var dt: Int? = null,
    @SerializedName("main") var main: MainForecast? = MainForecast(),
    @SerializedName("weather") var weather: MutableList<Weather> = mutableListOf(),
    @SerializedName("clouds") var clouds: Clouds? = Clouds(),
    @SerializedName("wind") var wind: WindForecast? = WindForecast(),
    @SerializedName("visibility") var visibility: Int? = null,
    @SerializedName("pop") var pop: Double? = null,
    @SerializedName("rain") var rain: Rain3h? = Rain3h(),
    @SerializedName("sys") var sys: SysForecast? = SysForecast(),
    @SerializedName("dt_txt") var dtTxt: String? = null
)