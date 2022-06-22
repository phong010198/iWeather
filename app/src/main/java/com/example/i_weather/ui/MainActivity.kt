package com.example.i_weather.ui

import android.content.Context
import android.content.SharedPreferences
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.i_weather.BR
import com.example.i_weather.R
import com.example.i_weather.base.BaseActivity
import com.example.i_weather.data.model.Coord
import com.example.i_weather.data.model.geo_coding.ResultGeocoding
import com.example.i_weather.databinding.ActivityMainBinding
import com.example.i_weather.ui.adapter.CityAdapter
import com.example.i_weather.ui.home.CurrentFragment
import com.example.i_weather.ui.home.ForecastFragment
import com.example.i_weather.util.gone
import com.example.i_weather.util.visible
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val layoutId: Int
        get() = R.layout.activity_main

    override val bindingVariable: Int
        get() = BR.viewModel

    override val viewModel: MainViewModel by viewModels()

    private lateinit var cityAdapter: CityAdapter
    private lateinit var sharedPreferences: SharedPreferences

    override fun initView() {
        viewDataBinding.root.setOnClickListener {
            hideKeyboard(it)
            viewDataBinding.rcvCity.gone()
            viewDataBinding.edtCity.clearFocus()
        }
        sharedPreferences = this.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE)
        viewModel.defaultCoord.value = Coord(
            sharedPreferences.getString("lon", "105.8516")!!.toDouble(),
            sharedPreferences.getString("lat", "21.0313")!!.toDouble()
        )
        viewDataBinding.swtTemperature.isChecked =
            sharedPreferences.getBoolean("isFahrenheit", false)
        viewModel.isFahrenheit.value = viewDataBinding.swtTemperature.isChecked
        viewModel.currentCity.value = sharedPreferences.getString("city", "Hanoi")
        viewModel.currentCoord.value = viewModel.defaultCoord.value

        viewDataBinding.btnSetHome.setOnClickListener { setAsHome() }
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewDataBinding.vp2Main.adapter = pagerAdapter

        viewDataBinding.swtTemperature.setOnCheckedChangeListener { switchButton, isChecked ->
            hideKeyboard(switchButton)
            viewDataBinding.rcvCity.gone()
            viewDataBinding.edtCity.clearFocus()
            viewModel.isFahrenheit.value = isChecked
        }
        viewDataBinding.imvClear.setOnClickListener { viewModel.city.value = "" }

        cityAdapter = CityAdapter(mutableListOf(), object : CityAdapter.CityAdapterCallback {
            override fun onItemClick(city: ResultGeocoding) {
                viewModel.currentCoord.value = Coord(city.lon, city.lat)
                viewModel.currentCity.value = city.name
                hideKeyboard(this@MainActivity.viewDataBinding.root)
                viewDataBinding.rcvCity.gone()
                viewDataBinding.edtCity.clearFocus()
                viewModel.city.value = ""
            }
        })
        viewDataBinding.rcvCity.layoutManager = LinearLayoutManager(this)
        viewDataBinding.rcvCity.adapter = cityAdapter
        viewDataBinding.edtCity.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            private var timer: Timer = Timer()
            private val DELAY: Long = 1000
            override fun afterTextChanged(s: Editable) {
                timer.cancel()
                timer = Timer()
                timer.schedule(
                    object : TimerTask() {
                        override fun run() {
                            this@MainActivity.runOnUiThread {
                                if (s.isNotBlank()) getCity(s.toString())
                                else viewDataBinding.rcvCity.gone()
                            }
                        }
                    }, DELAY
                )
            }
        }
        )
    }

    private fun setAsHome() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("lon", viewModel.resultCurrentWeather.value!!.coord!!.lon!!.toString())
        editor.putString("lat", viewModel.resultCurrentWeather.value!!.coord!!.lat!!.toString())
        editor.putString("city", viewModel.currentCity.value)
        viewModel.defaultCoord.value = Coord(
            viewModel.resultCurrentWeather.value!!.coord!!.lon!!,
            viewModel.resultCurrentWeather.value!!.coord!!.lat!!
        )
        viewModel.currentCity.value = viewModel.currentCity.value
        editor.apply()
        editor.commit()
        Toast.makeText(this, "New Home has been set", Toast.LENGTH_SHORT).show()
        viewDataBinding.btnSetHome.gone()
    }

    private fun getCity(city: String) {
        viewModel.getCity(city, {
        }, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            viewDataBinding.tvNoCity.gone()
        })
    }

    private fun getWeather(coord: Coord) {
        viewDataBinding.loadingView.visible()
        viewModel.getWeather(coord.lon!!, coord.lat!!, {
            viewDataBinding.loadingView.gone()
        }, {
            viewDataBinding.loadingView.gone()
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun getForecast(coord: Coord) {
        viewDataBinding.loadingViewForecast.visible()
        viewModel.getForecast(coord.lon!!, coord.lat!!, {
            viewDataBinding.loadingViewForecast.gone()
        }, {
            viewDataBinding.loadingViewForecast.gone()
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun updateColor(id: Int) {
        when (id) {
            in 200..299 -> {
                //Thunderstorm
                viewDataBinding.root.background = this.getDrawable(R.drawable.bg_thunderstorm)
            }
            in 300..399 -> {
                //Drizzle
                viewDataBinding.root.background = this.getDrawable(R.drawable.bg_thunderstorm)
            }
            in 500..599 -> {
                //Rain
                viewDataBinding.root.background = this.getDrawable(R.drawable.bg_rain)
            }
            in 600..699 -> {
                //Snow
                viewDataBinding.root.background = this.getDrawable(R.drawable.bg_rain)
            }
            in 700..799 -> {
                //Atmosphere
                viewDataBinding.root.background = this.getDrawable(R.drawable.bg_rain)
            }
            800 -> {
                //Clear
                viewDataBinding.root.background = this.getDrawable(R.drawable.bg_clear_day)
            }
            else -> {
                //Clouds
                viewDataBinding.root.background = this.getDrawable(R.drawable.bg_clouds)
            }
        }
    }

    override fun setupObserver() {
        viewModel.resultCity.observe(this) {
            cityAdapter.setData(it)
            with(viewDataBinding) {
                if (it.size > 0) {
                    rcvCity.visible()
                    tvNoCity.gone()
                } else {
                    rcvCity.gone()
                    tvNoCity.visible()
                }
            }
        }
        viewModel.currentCoord.observe(this) {
            getWeather(it)
            getForecast(it)
        }
        viewModel.city.observe(this) {
            viewDataBinding.imvClear.isVisible = !it.isNullOrEmpty()
            if (it.isNullOrEmpty()) viewDataBinding.tvNoCity.gone()
        }
        viewModel.currentCity.observe(this) {
            viewDataBinding.tvCity.text = it
        }
        viewModel.resultCurrentWeather.observe(this) {
            if (it.coord!!.lon == viewModel.defaultCoord.value!!.lon && it.coord!!.lat == viewModel.defaultCoord.value!!.lat) {
                viewDataBinding.btnSetHome.gone()
            } else {
                viewDataBinding.btnSetHome.visible()
            }
            updateColor(it.weather[0].id!!)
        }
    }

    override fun onPause() {
        super.onPause()
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean("isFahrenheit", viewDataBinding.swtTemperature.isChecked)
        editor.apply()
        editor.commit()
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                1 -> ForecastFragment.newInstance()
                else -> CurrentFragment.newInstance()
            }
        }
    }
}