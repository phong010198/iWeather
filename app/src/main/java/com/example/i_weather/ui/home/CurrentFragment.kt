package com.example.i_weather.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.i_weather.R
import com.example.i_weather.base.BaseFragmentWithoutViewModel
import com.example.i_weather.data.model.Coord
import com.example.i_weather.databinding.FragmentCurrentBinding
import com.example.i_weather.util.gone
import com.example.i_weather.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrentFragment : BaseFragmentWithoutViewModel<FragmentCurrentBinding>() {
    companion object {
        fun newInstance(): CurrentFragment {
            val args = Bundle()
            val fragment = CurrentFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_current

    override fun initView() {
        viewBinding.layoutRefresh.setOnRefreshListener {
            refreshWeather(activityViewModel.currentCoord.value!!)
        }
    }

    private fun refreshWeather(coord: Coord) {
        viewBinding.loadingView.visible()
        activityViewModel.getWeather(coord.lon!!, coord.lat!!, {
            viewBinding.loadingView.gone()
            viewBinding.layoutRefresh.isRefreshing = false
        }, {
            viewBinding.loadingView.gone()
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }

    @SuppressLint("SetTextI18n")
    override fun setupObserver() {
        activityViewModel.isFahrenheit.observe(viewLifecycleOwner) {
            if (activityViewModel.resultCurrentWeather.value != null) {
                viewBinding.tvTemperature.text =
                    if (it) String.format(
                        "%.2f°F",
                        activityViewModel.resultCurrentWeather.value!!.main!!.temp!! * 9 / 5 + 32
                    )
                    else "${activityViewModel.resultCurrentWeather.value!!.main!!.temp!!}°C"
            }
        }
        activityViewModel.resultCurrentWeather.observe(viewLifecycleOwner) {
            with(viewBinding) {
                tvWeather.text = it.weather[0].main
                tvTemperature.text =
                    if (activityViewModel.isFahrenheit.value == true) String.format(
                        "%.2f°F",
                        it.main!!.temp!! * 9 / 5 + 32
                    )
                    else "${it.main!!.temp!!}°C"
                Glide.with(requireContext())
                    .load("https://openweathermap.org/img/wn/" + it.weather[0].icon + "@2x.png")
                    .centerCrop()
                    .placeholder(R.drawable.ic_03d)
                    .into(viewBinding.imvWeather)
                tvHumidity.text = "${it.main!!.humidity}%"
                tvWind.text = "${it.wind!!.speed} km/h"
                val sdf = java.text.SimpleDateFormat("HH:mm")
                var date = java.util.Date(it.sys!!.sunrise!!.toLong() * 1000)
                tvSunrise.text = sdf.format(date)
                date = java.util.Date(it.sys!!.sunset!!.toLong() * 1000)
                tvSunset.text = sdf.format(date)
            }
        }
    }
}