package com.d2k.weatherforecasting.ui.home.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.d2k.weatherforecasting.ui.home.activity.MainActivity
import com.d2k.weatherforecasting.R
import com.d2k.weatherforecasting.databinding.FragmentWeatherForecastBinding
import com.d2k.weatherforecasting.ui.home.adapter.WeatherListAdapter
import com.d2k.weatherforecasting.ui.vm.WeatherVM
import com.d2k.weatherforecasting.utils.Constants
import com.d2k.weatherforecasting.utils.DataHandler
import com.d2k.weatherforecasting.utils.PrefUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeatherForecastFragment : Fragment(R.layout.fragment_weather_forecast) {

    lateinit var fragmentWeatherForecastBinding: FragmentWeatherForecastBinding

    val viewmodel :WeatherVM by viewModels()
    lateinit var fav: MenuItem
    lateinit var prefUtils: PrefUtils
    @Inject
    lateinit var weatherListAdapter: WeatherListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentWeatherForecastBinding = FragmentWeatherForecastBinding.bind(view)
        setHasOptionsMenu(true)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true);
        (activity as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(true);
        init()

        viewmodel.weatherReport.observe(viewLifecycleOwner,{
            it->
            when(it){
                is DataHandler.SUCCESS->{
                    weatherListAdapter.differ.submitList(it.data?.daily)
                }
                is DataHandler.ERROR->{

                }
                is DataHandler.LOADING->{

                }
            }
        })

        viewmodel.getWeatherReport()
    }

    private fun init() {
        fragmentWeatherForecastBinding.rcvWeatherList.apply {
            adapter = weatherListAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        prefUtils = PrefUtils(requireActivity())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.logout,menu)
        fav = menu.findItem(R.id.logout)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout ->{
                prefUtils.setBoolean(Constants.isLogin,false)
                /*
                var intent = Intent(requireActivity(),LoginActivity::class.java)
                startActivity(intent)*/
                requireActivity().finish()
                findNavController().navigate(R.id.action_weatherForecastFragment_to_loginActivity)

                return true
            }
            else -> {}
        }
        return false
    }

}