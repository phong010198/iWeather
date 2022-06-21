package com.example.i_weather.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
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
                tvLocation.text = it.name
                tvCoordinate.text = "${it.coord!!.lon} | ${it.coord!!.lat}"
                tvWeather.text = "${it.weather[0].main} (${it.weather[0].description})"
                tvTemperature.text =
                    if (activityViewModel.isFahrenheit.value == true) String.format(
                        "%.2f°F",
                        it.main!!.temp!! * 9 / 5 + 32
                    )
                    else "${it.main!!.temp!!}°C"
                tvHumidity.text = "${it.main!!.humidity}%"
            }
        }
    }
}