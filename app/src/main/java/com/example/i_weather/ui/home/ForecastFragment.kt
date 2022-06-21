package com.example.i_weather.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.i_weather.R
import com.example.i_weather.base.BaseFragmentWithoutViewModel
import com.example.i_weather.data.model.Coord
import com.example.i_weather.databinding.FragmentForecastBinding
import com.example.i_weather.ui.adapter.ForecastAdapter
import com.example.i_weather.util.gone
import com.example.i_weather.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForecastFragment : BaseFragmentWithoutViewModel<FragmentForecastBinding>() {
    companion object {
        fun newInstance(): ForecastFragment {
            val args = Bundle()
            val fragment = ForecastFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_forecast

    private lateinit var forecastAdapter: ForecastAdapter

    override fun initView() {
        forecastAdapter =
            ForecastAdapter(mutableListOf(), activityViewModel.isFahrenheit.value == true)
        viewBinding.rcvForecast.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.rcvForecast.adapter = forecastAdapter
        viewBinding.layoutRefresh.setOnRefreshListener {
            refreshForecast(activityViewModel.currentCoord.value!!)
        }
    }

    private fun refreshForecast(coord: Coord) {
        viewBinding.loadingView.visible()
        activityViewModel.getForecast(coord.lon!!, coord.lat!!, {
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
            forecastAdapter.changeCF(it)
        }
        activityViewModel.resultForecast.observe(viewLifecycleOwner) {
            forecastAdapter.setData(it.list)
        }
    }
}