package com.example.i_weather.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.i_weather.R
import com.example.i_weather.data.model.Forecast
import com.example.i_weather.databinding.ItemForecastBinding

class ForecastAdapter(
    private var data: MutableList<Forecast>,
    private var isFahrenheit: Boolean
) : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemForecastBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_forecast, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val sdf = java.text.SimpleDateFormat("MM-dd HH:mm")
            val date = java.util.Date(data[position].dt!!.toLong() * 1000)
            binding.tvTime.text = sdf.format(date)
            Glide.with(binding.root.context)
                .load("https://openweathermap.org/img/wn/" + data[position].weather[0].icon + "@2x.png")
                .centerCrop()
                .placeholder(R.drawable.ic_03d)
                .into(binding.imvWeather)
            binding.tvTemperature.text =
                if (isFahrenheit) String.format(
                    "%.2f°F",
                    data[position].main!!.temp!! * 9 / 5 + 32
                )
                else "${data[position].main!!.temp!!}°C"
            binding.tvHumidity.text = "${data[position].main!!.humidity}%"
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun changeCF(isFahrenheit: Boolean) {
        this.isFahrenheit = isFahrenheit
        notifyDataSetChanged()
    }

    fun setData(data: MutableList<Forecast>) {
        this.data = data
        notifyDataSetChanged()
    }
}