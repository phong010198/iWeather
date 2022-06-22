package com.example.i_weather.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.i_weather.R
import com.example.i_weather.data.model.geo_coding.ResultGeocoding
import com.example.i_weather.databinding.ItemCityBinding
import java.util.*

class CityAdapter(
    private var data: MutableList<ResultGeocoding>,
    private val callback: CityAdapterCallback
) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {
    interface CityAdapterCallback {
        fun onItemClick(city: ResultGeocoding)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemCityBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_city, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.tvCity.text = data[position].name
            if (data[position].country != null) {
                val loc = Locale("", data[position].country!!)
                binding.tvCountry.text = loc.displayCountry
            } else binding.tvCountry.text = ""
            binding.root.setOnClickListener { callback.onItemClick(data[position]) }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data: MutableList<ResultGeocoding>) {
        this.data = data
        notifyDataSetChanged()
    }
}