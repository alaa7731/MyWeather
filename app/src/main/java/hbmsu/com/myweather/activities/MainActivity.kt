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
import com.johnhiott.darkskyandroidlib.models.DataPoint
import hbmsu.com.myweather.model.WeatherData
import android.app.AlarmManager
import android.content.Context.ALARM_SERVICE
import android.app.PendingIntent
import android.content.Context
import android.content.res.Configuration
import android.os.PersistableBundle
import hbmsu.com.myweather.custom.NotificationReceiver
import retrofit.client.Response
import java.util.*


class MainActivity : AppCompatActivity() {
    private var requestCodeSettings = 1234
    private lateinit var weatherRequestBuilder: RequestBuilder
    var weatherKey = "weatherObj"
    var weather: WeatherResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Connect.getInstance().init(this@MainActivity)
        ForecastApi.create("e551edd89cf2e1ce3b92533b222b330c")
        weatherRequestBuilder = RequestBuilder()

        if (weather == null)
            getWeather()

        ivSetting.setOnClickListener {
            startActivityForResult(Intent(this@MainActivity, SettingsActivity::class.java), requestCodeSettings)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)

        if(weather!=null){
            buildWeather()
        }
    }
    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        if (weather != null)
            outState!!.putString(weatherKey, Utils.getStringFromModel(weather))
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState!!.containsKey(weatherKey)) {
            weather = Utils.getModel(savedInstanceState!!.getString(weatherKey), WeatherResponse::class.java)
            buildWeather()
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


    private var data: WeatherData? = null

    private fun getWeather() {

        val request = generateWeatherBuilder()

        Utils.showLoading(this@MainActivity)
        weatherRequestBuilder.getWeather(request, object : Callback<WeatherResponse> {
            override fun success(weatherRes: WeatherResponse?, response: Response?) {
                Utils.hideLoading()
                if (weatherRes != null && weatherRes.daily != null && weatherRes.daily.data != null && weatherRes.daily.data.size > 0) {
                    weather = weatherRes!!
                    buildWeather()
                }
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

    private fun buildWeather() {

        var adapter = WeatherRVAdapter(this@MainActivity) { position, view ->
            getDayWeather(weather!!.daily.data[position].time, view)

        }
        rvWeather.adapter = adapter
        rvWeather.itemAnimator = SlideInRightAnimator()
        Handler().postDelayed({ adapter.addItems(weather!!.daily.data) }, 200)

        generateLocalNotifications(weather!!.daily.data)
    }

    private fun generateLocalNotifications(data: List<DataPoint>) {
        var size = data.size
        if (size > 2) {
            var middle = size / 2
            registerNotification(data[middle])
            registerNotification(data[size - 1])
        } else {
            registerNotification(data[size - 1])
        }
    }

    private fun registerNotification(dataPoint: DataPoint) {
        val alarmIntent = Intent(this, NotificationReceiver::class.java)
        alarmIntent.putExtra("object", Utils.getStringFromModel(dataPoint))
        val pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0)

        val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = dataPoint.time


        manager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
                pendingIntent)

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
        weatherRequestBuilder.getWeather(request, object : Callback<WeatherResponse> {
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
