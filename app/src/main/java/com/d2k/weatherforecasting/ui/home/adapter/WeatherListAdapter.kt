package com.d2k.weatherforecasting.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.d2k.weatherforecasting.databinding.AdapterUserListBinding
import com.d2k.weatherforecasting.databinding.AdapterWeatherListBinding
import com.d2k.weatherforecasting.databinding.FragmentWeatherForecastBinding
import com.d2k.weatherforecasting.model.Daily
import javax.inject.Inject

class WeatherListAdapter @Inject constructor() : RecyclerView.Adapter<WeatherListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: AdapterWeatherListBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Daily>() {
        override fun areItemsTheSame(oldItem: Daily, newItem: Daily): Boolean {

            return false
        }

        override fun areContentsTheSame(oldItem: Daily, newItem: Daily): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AdapterWeatherListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Daily = differ.currentList[position]
        holder.binding.apply {
            tvTemp.text = "Day :"+ Daily.temp?.day.toString()+", Night"+ Daily.temp?.night.toString()
            tvHumidity.text = Daily.humidity.toString()
            tvWeatherType.text = Daily.weather?.get(0)?.description.toString()
            tvWindSpeed.text = Daily.wind_speed.toString()

        }

        /*holder.itemView.setOnClickListener {
            setDailyClickListener?.let {
                it(Daily)
            }
        }*/

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var setDailyClickListener: ((Daily: Daily) -> Unit)? = null

    fun onDailyClicked(listener: (Daily) -> Unit) {
        setDailyClickListener = listener
    }
    fun removeAt(position: Int) {
        differ.currentList.removeAt(position)
        notifyItemRemoved(position)
    }
}