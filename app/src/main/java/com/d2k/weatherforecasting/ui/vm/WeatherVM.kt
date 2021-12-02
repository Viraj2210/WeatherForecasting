package com.d2k.weatherforecasting.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.d2k.weatherforecasting.di.WeatherRepository
import com.d2k.weatherforecasting.model.Weather
import com.d2k.weatherforecasting.model.WeatherResponse
import com.d2k.weatherforecasting.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
 class WeatherVM @Inject constructor(private val weatherrepository: WeatherRepository) : ViewModel() {

    private val _weatherReport = MutableLiveData<DataHandler<WeatherResponse>>()
    val weatherReport: LiveData<DataHandler<WeatherResponse>> = _weatherReport

    fun getWeatherReport() {
        _weatherReport.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = weatherrepository.getWeatherReport()
            _weatherReport.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<WeatherResponse>): DataHandler<WeatherResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it ->
                return DataHandler.SUCCESS(it)
            }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }
}