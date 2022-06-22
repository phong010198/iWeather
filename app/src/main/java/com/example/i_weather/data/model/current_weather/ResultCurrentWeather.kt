package com.example.i_weather.data.model.current_weather

import com.example.i_weather.data.model.*
import com.google.gson.annotations.SerializedName

data class ResultCurrentWeather(
    @SerializedName("coord") var coord: Coord? = Coord(),
    @SerializedName("weather") var weather: MutableList<Weather> = mutableListOf(),
    @SerializedName("base") var base: String? = null,
    @SerializedName("main") var main: MainCurrentWeather? = MainCurrentWeather(),
    @SerializedName("visibility") var visibility: Int? = null,
    @SerializedName("wind") var wind: WindCurrentWeather? = WindCurrentWeather(),
    @SerializedName("rain") var rain: Rain1h? = Rain1h(),
    @SerializedName("clouds") var clouds: Clouds? = Clouds(),
    @SerializedName("dt") var dt: Int? = null,
    @SerializedName("sys") var sys: SysCurrentWeather? = SysCurrentWeather(),
    @SerializedName("timezone") var timezone: Int? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("cod") var cod: Int? = null
)