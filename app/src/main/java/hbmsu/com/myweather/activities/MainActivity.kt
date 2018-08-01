package hbmsu.com.myweather.activities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.johnhiott.darkskyandroidlib.ForecastApi
import hbmsu.com.myweather.adapters.WeatherRVAdapter
import hbmsu.com.myweather.connect.Connect
import kotlinx.android.synthetic.main.activity_main.*
import retrofit.RetrofitError
import com.johnhiott.darkskyandroidlib.models.WeatherResponse
import com.johnhiott.darkskyandroidlib.RequestBuilder
import com.johnhiott.darkskyandroidlib.models.Request
import hbmsu.com.myweather.R
import hbmsu.com.myweather.Utils
import hbmsu.com.myweather.custom.SlideInRightAnimator
import retrofit.Callback
import android.support.v4.view.ViewCompat
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import hbmsu.com.myweather.model.WeatherData


class MainActivity : AppCompatActivity() {
    var requestCodeSettings = 1234
    private lateinit var weather: RequestBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Connect.getInstance().init(this@MainActivity)
        ForecastApi.create("e551edd89cf2e1ce3b92533b222b330c")
        weather = RequestBuilder()

        getWeather()

        ivSetting.setOnClickListener {
            startActivityForResult(Intent(this@MainActivity, SettingsActivity::class.java), requestCodeSettings)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == requestCodeSettings) {
                getWeather()
            }
        }
    }

    var staticData = "[\n" +
            "  {\n" +
            "\"time\": 1532980800,\n" +
            "\"summary\": \"Partly cloudy until evening and humid throughout the day.\",\n" +
            "\"icon\": \"partly-cloudy-day\",\n" +
            "\"sunriseTime\": 1533001706,\n" +
            "\"sunsetTime\": 1533049635,\n" +
            "\"moonPhase\": 0.61,\n" +
            "\"precipIntensity\": 0,\n" +
            "\"precipIntensityMax\": 0.0001,\n" +
            "\"precipIntensityMaxTime\": 1533049200,\n" +
            "\"precipProbability\": 0,\n" +
            "\"temperatureHigh\": 104.71,\n" +
            "\"temperatureHighTime\": 1533024000,\n" +
            "\"temperatureLow\": 88.33,\n" +
            "\"temperatureLowTime\": 1533088800,\n" +
            "\"apparentTemperatureHigh\": 120.56,\n" +
            "\"apparentTemperatureHighTime\": 1533042000,\n" +
            "\"apparentTemperatureLow\": 107.07,\n" +
            "\"apparentTemperatureLowTime\": 1533088800,\n" +
            "\"dewPoint\": 71.15,\n" +
            "\"humidity\": 0.45,\n" +
            "\"pressure\": 996.74,\n" +
            "\"windSpeed\": 2.85,\n" +
            "\"windGust\": 15.21,\n" +
            "\"windGustTime\": 1533027600,\n" +
            "\"windBearing\": 299,\n" +
            "\"cloudCover\": 0.28,\n" +
            "\"uvIndex\": 11,\n" +
            "\"uvIndexTime\": 1533024000,\n" +
            "\"visibility\": 5.12,\n" +
            "\"ozone\": 293.86,\n" +
            "\"temperatureMin\": 92.85,\n" +
            "\"temperatureMinTime\": 1532998800,\n" +
            "\"temperatureMax\": 104.71,\n" +
            "\"temperatureMaxTime\": 1533024000,\n" +
            "\"apparentTemperatureMin\": 96.03,\n" +
            "\"apparentTemperatureMinTime\": 1533002400,\n" +
            "\"apparentTemperatureMax\": 120.56,\n" +
            "\"apparentTemperatureMaxTime\": 1533042000\n" +
            "},\n" +
            "  {\n" +
            "\"time\": 1533067200,\n" +
            "\"summary\": \"Humid throughout the day and partly cloudy in the morning.\",\n" +
            "\"icon\": \"partly-cloudy-day\",\n" +
            "\"sunriseTime\": 1533088135,\n" +
            "\"sunsetTime\": 1533136001,\n" +
            "\"moonPhase\": 0.64,\n" +
            "\"precipIntensity\": 0.0001,\n" +
            "\"precipIntensityMax\": 0.0001,\n" +
            "\"precipIntensityMaxTime\": 1533106800,\n" +
            "\"precipProbability\": 0.04,\n" +
            "\"precipType\": \"rain\",\n" +
            "\"temperatureHigh\": 94.52,\n" +
            "\"temperatureHighTime\": 1533124800,\n" +
            "\"temperatureLow\": 88,\n" +
            "\"temperatureLowTime\": 1533171600,\n" +
            "\"apparentTemperatureHigh\": 112.15,\n" +
            "\"apparentTemperatureHighTime\": 1533114000,\n" +
            "\"apparentTemperatureLow\": 106.32,\n" +
            "\"apparentTemperatureLowTime\": 1533171600,\n" +
            "\"dewPoint\": 80.06,\n" +
            "\"humidity\": 0.69,\n" +
            "\"pressure\": 996.98,\n" +
            "\"windSpeed\": 3.58,\n" +
            "\"windGust\": 13.25,\n" +
            "\"windGustTime\": 1533117600,\n" +
            "\"windBearing\": 287,\n" +
            "\"cloudCover\": 0.14,\n" +
            "\"uvIndex\": 13,\n" +
            "\"uvIndexTime\": 1533110400,\n" +
            "\"visibility\": 10,\n" +
            "\"ozone\": 291.3,\n" +
            "\"temperatureMin\": 88.33,\n" +
            "\"temperatureMinTime\": 1533088800,\n" +
            "\"temperatureMax\": 94.52,\n" +
            "\"temperatureMaxTime\": 1533124800,\n" +
            "\"apparentTemperatureMin\": 107.07,\n" +
            "\"apparentTemperatureMinTime\": 1533088800,\n" +
            "\"apparentTemperatureMax\": 113.84,\n" +
            "\"apparentTemperatureMaxTime\": 1533146400\n" +
            "},\n" +
            "  {\n" +
            "\"time\": 1533153600,\n" +
            "\"summary\": \"Humid throughout the day and partly cloudy starting in the evening.\",\n" +
            "\"icon\": \"partly-cloudy-night\",\n" +
            "\"sunriseTime\": 1533174564,\n" +
            "\"sunsetTime\": 1533222365,\n" +
            "\"moonPhase\": 0.67,\n" +
            "\"precipIntensity\": 0,\n" +
            "\"precipIntensityMax\": 0.0001,\n" +
            "\"precipIntensityMaxTime\": 1533193200,\n" +
            "\"precipProbability\": 0,\n" +
            "\"temperatureHigh\": 94.1,\n" +
            "\"temperatureHighTime\": 1533207600,\n" +
            "\"temperatureLow\": 89.74,\n" +
            "\"temperatureLowTime\": 1533261600,\n" +
            "\"apparentTemperatureHigh\": 112.8,\n" +
            "\"apparentTemperatureHighTime\": 1533222000,\n" +
            "\"apparentTemperatureLow\": 106.79,\n" +
            "\"apparentTemperatureLowTime\": 1533265200,\n" +
            "\"dewPoint\": 80.39,\n" +
            "\"humidity\": 0.71,\n" +
            "\"pressure\": 997.03,\n" +
            "\"windSpeed\": 3.26,\n" +
            "\"windGust\": 15.74,\n" +
            "\"windGustTime\": 1533211200,\n" +
            "\"windBearing\": 281,\n" +
            "\"cloudCover\": 0.06,\n" +
            "\"uvIndex\": 13,\n" +
            "\"uvIndexTime\": 1533196800,\n" +
            "\"visibility\": 10,\n" +
            "\"ozone\": 286.3,\n" +
            "\"temperatureMin\": 88,\n" +
            "\"temperatureMinTime\": 1533171600,\n" +
            "\"temperatureMax\": 94.1,\n" +
            "\"temperatureMaxTime\": 1533207600,\n" +
            "\"apparentTemperatureMin\": 106.32,\n" +
            "\"apparentTemperatureMinTime\": 1533171600,\n" +
            "\"apparentTemperatureMax\": 113.85,\n" +
            "\"apparentTemperatureMaxTime\": 1533229200\n" +
            "},\n" +
            "  {\n" +
            "\"time\": 1533240000,\n" +
            "\"summary\": \"Humid throughout the day and mostly cloudy starting in the afternoon.\",\n" +
            "\"icon\": \"partly-cloudy-night\",\n" +
            "\"sunriseTime\": 1533260993,\n" +
            "\"sunsetTime\": 1533308729,\n" +
            "\"moonPhase\": 0.7,\n" +
            "\"precipIntensity\": 0,\n" +
            "\"precipIntensityMax\": 0.0001,\n" +
            "\"precipIntensityMaxTime\": 1533272400,\n" +
            "\"precipProbability\": 0,\n" +
            "\"temperatureHigh\": 94.92,\n" +
            "\"temperatureHighTime\": 1533294000,\n" +
            "\"temperatureLow\": 89.2,\n" +
            "\"temperatureLowTime\": 1533344400,\n" +
            "\"apparentTemperatureHigh\": 112.01,\n" +
            "\"apparentTemperatureHighTime\": 1533308400,\n" +
            "\"apparentTemperatureLow\": 106.08,\n" +
            "\"apparentTemperatureLowTime\": 1533348000,\n" +
            "\"dewPoint\": 79.53,\n" +
            "\"humidity\": 0.67,\n" +
            "\"pressure\": 998.83,\n" +
            "\"windSpeed\": 3.91,\n" +
            "\"windGust\": 17.11,\n" +
            "\"windGustTime\": 1533294000,\n" +
            "\"windBearing\": 292,\n" +
            "\"cloudCover\": 0.25,\n" +
            "\"uvIndex\": 13,\n" +
            "\"uvIndexTime\": 1533283200,\n" +
            "\"visibility\": 10,\n" +
            "\"ozone\": 282.66,\n" +
            "\"temperatureMin\": 89.74,\n" +
            "\"temperatureMinTime\": 1533261600,\n" +
            "\"temperatureMax\": 94.92,\n" +
            "\"temperatureMaxTime\": 1533294000,\n" +
            "\"apparentTemperatureMin\": 106.79,\n" +
            "\"apparentTemperatureMinTime\": 1533265200,\n" +
            "\"apparentTemperatureMax\": 113.52,\n" +
            "\"apparentTemperatureMaxTime\": 1533319200\n" +
            "},\n" +
            "  {\n" +
            "\"time\": 1533326400,\n" +
            "\"summary\": \"Humid and mostly cloudy throughout the day.\",\n" +
            "\"icon\": \"partly-cloudy-day\",\n" +
            "\"sunriseTime\": 1533347422,\n" +
            "\"sunsetTime\": 1533395091,\n" +
            "\"moonPhase\": 0.74,\n" +
            "\"precipIntensity\": 0.0001,\n" +
            "\"precipIntensityMax\": 0.0002,\n" +
            "\"precipIntensityMaxTime\": 1533405600,\n" +
            "\"precipProbability\": 0.03,\n" +
            "\"precipType\": \"rain\",\n" +
            "\"temperatureHigh\": 96.84,\n" +
            "\"temperatureHighTime\": 1533384000,\n" +
            "\"temperatureLow\": 88.75,\n" +
            "\"temperatureLowTime\": 1533427200,\n" +
            "\"apparentTemperatureHigh\": 111.85,\n" +
            "\"apparentTemperatureHighTime\": 1533373200,\n" +
            "\"apparentTemperatureLow\": 103.92,\n" +
            "\"apparentTemperatureLowTime\": 1533430800,\n" +
            "\"dewPoint\": 79.03,\n" +
            "\"humidity\": 0.65,\n" +
            "\"pressure\": 999.86,\n" +
            "\"windSpeed\": 4.45,\n" +
            "\"windGust\": 15.94,\n" +
            "\"windGustTime\": 1533380400,\n" +
            "\"windBearing\": 289,\n" +
            "\"cloudCover\": 0.65,\n" +
            "\"uvIndex\": 11,\n" +
            "\"uvIndexTime\": 1533369600,\n" +
            "\"visibility\": 10,\n" +
            "\"ozone\": 277.86,\n" +
            "\"temperatureMin\": 89.2,\n" +
            "\"temperatureMinTime\": 1533344400,\n" +
            "\"temperatureMax\": 96.84,\n" +
            "\"temperatureMaxTime\": 1533384000,\n" +
            "\"apparentTemperatureMin\": 106.08,\n" +
            "\"apparentTemperatureMinTime\": 1533348000,\n" +
            "\"apparentTemperatureMax\": 112.81,\n" +
            "\"apparentTemperatureMaxTime\": 1533326400\n" +
            "},\n" +
            "  {\n" +
            "\"time\": 1533412800,\n" +
            "\"summary\": \"Humid and overcast throughout the day.\",\n" +
            "\"icon\": \"cloudy\",\n" +
            "\"sunriseTime\": 1533433851,\n" +
            "\"sunsetTime\": 1533481452,\n" +
            "\"moonPhase\": 0.77,\n" +
            "\"precipIntensity\": 0.0001,\n" +
            "\"precipIntensityMax\": 0.0002,\n" +
            "\"precipIntensityMaxTime\": 1533488400,\n" +
            "\"precipProbability\": 0.03,\n" +
            "\"precipType\": \"rain\",\n" +
            "\"temperatureHigh\": 97.8,\n" +
            "\"temperatureHighTime\": 1533466800,\n" +
            "\"temperatureLow\": 90.42,\n" +
            "\"temperatureLowTime\": 1533513600,\n" +
            "\"apparentTemperatureHigh\": 114.29,\n" +
            "\"apparentTemperatureHighTime\": 1533481200,\n" +
            "\"apparentTemperatureLow\": 108.85,\n" +
            "\"apparentTemperatureLowTime\": 1533517200,\n" +
            "\"dewPoint\": 78.85,\n" +
            "\"humidity\": 0.64,\n" +
            "\"pressure\": 999.49,\n" +
            "\"windSpeed\": 7.7,\n" +
            "\"windGust\": 16.28,\n" +
            "\"windGustTime\": 1533452400,\n" +
            "\"windBearing\": 252,\n" +
            "\"cloudCover\": 0.92,\n" +
            "\"uvIndex\": 8,\n" +
            "\"uvIndexTime\": 1533456000,\n" +
            "\"visibility\": 10,\n" +
            "\"ozone\": 274.32,\n" +
            "\"temperatureMin\": 88.75,\n" +
            "\"temperatureMinTime\": 1533427200,\n" +
            "\"temperatureMax\": 97.8,\n" +
            "\"temperatureMaxTime\": 1533466800,\n" +
            "\"apparentTemperatureMin\": 103.92,\n" +
            "\"apparentTemperatureMinTime\": 1533430800,\n" +
            "\"apparentTemperatureMax\": 114.44,\n" +
            "\"apparentTemperatureMaxTime\": 1533488400\n" +
            "},\n" +
            "  {\n" +
            "\"time\": 1533499200,\n" +
            "\"summary\": \"Humid and mostly cloudy throughout the day.\",\n" +
            "\"icon\": \"partly-cloudy-day\",\n" +
            "\"sunriseTime\": 1533520279,\n" +
            "\"sunsetTime\": 1533567812,\n" +
            "\"moonPhase\": 0.81,\n" +
            "\"precipIntensity\": 0,\n" +
            "\"precipIntensityMax\": 0.0002,\n" +
            "\"precipIntensityMaxTime\": 1533546000,\n" +
            "\"precipProbability\": 0,\n" +
            "\"temperatureHigh\": 97.91,\n" +
            "\"temperatureHighTime\": 1533553200,\n" +
            "\"temperatureLow\": 89.98,\n" +
            "\"temperatureLowTime\": 1533607200,\n" +
            "\"apparentTemperatureHigh\": 114.01,\n" +
            "\"apparentTemperatureHighTime\": 1533535200,\n" +
            "\"apparentTemperatureLow\": 104.54,\n" +
            "\"apparentTemperatureLowTime\": 1533610800,\n" +
            "\"dewPoint\": 79.07,\n" +
            "\"humidity\": 0.63,\n" +
            "\"pressure\": 997.79,\n" +
            "\"windSpeed\": 6.51,\n" +
            "\"windGust\": 12.79,\n" +
            "\"windGustTime\": 1533528000,\n" +
            "\"windBearing\": 255,\n" +
            "\"cloudCover\": 0.81,\n" +
            "\"uvIndex\": 8,\n" +
            "\"uvIndexTime\": 1533542400,\n" +
            "\"visibility\": 10,\n" +
            "\"ozone\": 275.31,\n" +
            "\"temperatureMin\": 90.42,\n" +
            "\"temperatureMinTime\": 1533513600,\n" +
            "\"temperatureMax\": 97.91,\n" +
            "\"temperatureMaxTime\": 1533553200,\n" +
            "\"apparentTemperatureMin\": 108.85,\n" +
            "\"apparentTemperatureMinTime\": 1533517200,\n" +
            "\"apparentTemperatureMax\": 114.01,\n" +
            "\"apparentTemperatureMaxTime\": 1533535200\n" +
            "},\n" +
            "  {\n" +
            "\"time\": 1533585600,\n" +
            "\"summary\": \"Humid throughout the day and mostly cloudy until evening.\",\n" +
            "\"icon\": \"partly-cloudy-day\",\n" +
            "\"sunriseTime\": 1533606708,\n" +
            "\"sunsetTime\": 1533654171,\n" +
            "\"moonPhase\": 0.85,\n" +
            "\"precipIntensity\": 0.0001,\n" +
            "\"precipIntensityMax\": 0.0002,\n" +
            "\"precipIntensityMaxTime\": 1533664800,\n" +
            "\"precipProbability\": 0.04,\n" +
            "\"precipType\": \"rain\",\n" +
            "\"temperatureHigh\": 95.72,\n" +
            "\"temperatureHighTime\": 1533639600,\n" +
            "\"temperatureLow\": 89.74,\n" +
            "\"temperatureLowTime\": 1533682800,\n" +
            "\"apparentTemperatureHigh\": 113.57,\n" +
            "\"apparentTemperatureHighTime\": 1533654000,\n" +
            "\"apparentTemperatureLow\": 107.2,\n" +
            "\"apparentTemperatureLowTime\": 1533690000,\n" +
            "\"dewPoint\": 79.23,\n" +
            "\"humidity\": 0.65,\n" +
            "\"pressure\": 996.57,\n" +
            "\"windSpeed\": 3.37,\n" +
            "\"windGust\": 11.93,\n" +
            "\"windGustTime\": 1533643200,\n" +
            "\"windBearing\": 257,\n" +
            "\"cloudCover\": 0.57,\n" +
            "\"uvIndex\": 10,\n" +
            "\"uvIndexTime\": 1533632400,\n" +
            "\"visibility\": 10,\n" +
            "\"ozone\": 281.82,\n" +
            "\"temperatureMin\": 89.98,\n" +
            "\"temperatureMinTime\": 1533607200,\n" +
            "\"temperatureMax\": 95.72,\n" +
            "\"temperatureMaxTime\": 1533639600,\n" +
            "\"apparentTemperatureMin\": 104.54,\n" +
            "\"apparentTemperatureMinTime\": 1533610800,\n" +
            "\"apparentTemperatureMax\": 113.67,\n" +
            "\"apparentTemperatureMaxTime\": 1533657600\n" +
            "}\n" +
            "]"
    private var data: WeatherData? = null

    private fun getWeather() {

        val request = generateWeatherBuilder()

        Utils.showLoading(this@MainActivity)
        weather.getWeather(request, object : Callback<WeatherResponse> {
            override fun success(weather: WeatherResponse?, response: retrofit.client.Response?) {
                Utils.hideLoading()
                var adapter = WeatherRVAdapter(this@MainActivity) { position, view ->
                    getDayWeather(weather!!.daily.data[position].time, view)

                }
                rvWeather.adapter = adapter
                rvWeather.itemAnimator = SlideInRightAnimator()
                Handler().postDelayed({ adapter.addItems(weather!!.daily.data) }, 200)


            }


            override fun failure(retrofitError: RetrofitError) {
                Utils.hideLoading()
                Log.d("TAAAG", "Error while calling: " + retrofitError.url)
            }
        })
//        Connect.getInstance().getWeather(Connect.getInstance().selectedUnit) {
//
//            data = Utils.getModel(staticData, WeatherData::class.java)
//
//
//            var adapter = WeatherRVAdapter(this@MainActivity, ViewClick { position, view -> })
//            rvWeather.adapter = adapter
//
////            adapter.addItems(data)
//        }
    }

    private fun generateWeatherBuilder(time: Long = -1): Request {

        val request = Request()
        request.lat = "25.0750853"
        request.lng = "54.947555"
        request.units = Connect.getInstance().selectedUnit
        request.language = Request.Language.ENGLISH
        request.addExcludeBlock(Request.Block.MINUTELY)
        if (time != -1L) {
            request.time = time.toString()
        }

        return request
    }

    private fun getDayWeather(time: Long, view: View) {
        val request = generateWeatherBuilder(time)

        Utils.showLoading(this@MainActivity)
        weather.getWeather(request, object : Callback<WeatherResponse> {
            override fun success(weather: WeatherResponse?, response: retrofit.client.Response?) {
                Utils.hideLoading()
                val intent = Intent(this@MainActivity, WeatherDetailsActivity::class.java)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivity,
                        view,
                        ViewCompat.getTransitionName(view))
                intent.putExtra("object", Utils.getStringFromModel(weather!!))
                startActivity(intent, options.toBundle())

            }


            override fun failure(retrofitError: RetrofitError) {
                Utils.hideLoading()
                Log.d("TAAAG", "Error while calling: " + retrofitError.url)
            }
        })


    }
}
