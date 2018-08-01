package hbmsu.com.myweather.activities

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.johnhiott.darkskyandroidlib.models.DataBlock
import com.johnhiott.darkskyandroidlib.models.WeatherResponse
import hbmsu.com.myweather.R
import hbmsu.com.myweather.Utils
import kotlinx.android.synthetic.main.activity_weather_details.*
import okhttp3.internal.Util
import java.lang.Double

class WeatherDetailsActivity : AppCompatActivity() {
    lateinit var weatherDetail: WeatherResponse
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_details)
        weatherDetail = Utils.getModel(intent.getStringExtra("object"), WeatherResponse::class.java)

        fillData()

    }

    @SuppressLint("SetTextI18n")
    private fun fillData() {
        if (weatherDetail != null && weatherDetail.currently != null) {
            tvDate.text = Utils.formatMilliToDate(weatherDetail.currently.time)
            tvDegree.text = String.format(getString(R.string.degree), weatherDetail.currently.temperature.toInt(), Utils.getDegreeUnit())
            tvMaxDegree.text = String.format(getString(R.string.degree),
                    weatherDetail.currently.temperatureMax.toInt(), Utils.getDegreeUnit())
            tvMinDegree.text = String.format(getString(R.string.degree),
                    weatherDetail.currently.temperatureMin.toInt(), Utils.getDegreeUnit())
            tvSummary.text = weatherDetail.currently.summary
            try {
                tvHumidity.text = (Double.parseDouble(weatherDetail.currently.humidity) * 100).toInt().toString() + "%"
            } catch (e: Exception) {
                e.printStackTrace()
                tvHumidity.text = weatherDetail.currently.humidity + "%"

            }

            tvVisibility.text = Utils.getVisibility(weatherDetail.currently.visibility)
            //            tvVisibility.setText( weatherDetail.getVisibility());

            buildTodayTemperatures()
        }
    }

    private fun buildTodayTemperatures() {
        llHourlyWeather.removeAllViews()
        if (weatherDetail.hourly != null && weatherDetail.hourly.data != null && weatherDetail.hourly.data.size > 0) {
            var hourlyData = weatherDetail.hourly.data

            hourlyData.forEach {
                var view = LayoutInflater.from(this@WeatherDetailsActivity).inflate(R.layout.daily_temp_item, null)
                var tvDate = view.findViewById<TextView>(R.id.tvDate)
                var tvTemp = view.findViewById<TextView>(R.id.tvTemp)
                var tvSummary = view.findViewById<TextView>(R.id.tvSummary)

                tvDate.text = Utils.formatMilliToTime(it.time.toInt())
                tvTemp.text = String.format(getString(R.string.degree), it.temperature.toInt(), Utils.getDegreeUnit())
                tvSummary.text = it.summary

                llHourlyWeather.addView(view)
            }
        }
    }
}
