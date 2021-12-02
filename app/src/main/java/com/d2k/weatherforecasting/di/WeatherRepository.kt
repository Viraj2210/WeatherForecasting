package com.d2k.weatherforecasting.di

import com.d2k.weatherforecasting.api.WeatherApi
import com.d2k.weatherforecasting.model.WeatherResponse
import retrofit2.Response
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    val weatherApi: WeatherApi
) {
    suspend fun getWeatherReport(): Response<WeatherResponse> {
        return weatherApi.getWeatherReport()
    }

}