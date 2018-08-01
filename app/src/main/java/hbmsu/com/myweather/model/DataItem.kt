package hbmsu.com.myweather.model

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

import java.io.Serializable

@Generated("com.asif.gsonpojogenerator")
class DataItem : Serializable {

    @SerializedName("windGust")
    var windGust: Double = 0.toDouble()

    @SerializedName("apparentTemperatureMinTime")
    var apparentTemperatureMinTime: Int = 0

    @SerializedName("temperatureMax")
    var temperatureMax: Double = 0.toDouble()

    @SerializedName("icon")
    var icon: String? = null

    @SerializedName("precipIntensityMax")
    var precipIntensityMax: Double = 0.toDouble()

    @SerializedName("windBearing")
    var windBearing: Int = 0

    @SerializedName("ozone")
    var ozone: Double = 0.toDouble()

    @SerializedName("temperatureMaxTime")
    var temperatureMaxTime: Int = 0

    @SerializedName("apparentTemperatureMin")
    var apparentTemperatureMin: Double = 0.toDouble()

    @SerializedName("sunsetTime")
    var sunsetTime: Int = 0

    @SerializedName("temperatureLow")
    var temperatureLow: Double = 0.toDouble()

    @SerializedName("precipType")
    var precipType: String? = null

    @SerializedName("humidity")
    var humidity: Double = 0.toDouble()

    @SerializedName("moonPhase")
    var moonPhase: Double = 0.toDouble()

    @SerializedName("windSpeed")
    var windSpeed: Double = 0.toDouble()

    @SerializedName("apparentTemperatureLowTime")
    var apparentTemperatureLowTime: Int = 0

    @SerializedName("sunriseTime")
    var sunriseTime: Int = 0

    @SerializedName("apparentTemperatureLow")
    var apparentTemperatureLow: Double = 0.toDouble()

    @SerializedName("summary")
    var summary: String? = null

    @SerializedName("precipProbability")
    var precipProbability: Double = 0.toDouble()

    @SerializedName("temperatureHighTime")
    var temperatureHighTime: Int = 0

    @SerializedName("visibility")
    var visibility: Double = 0.toDouble()

    @SerializedName("precipIntensity")
    var precipIntensity: Double = 0.toDouble()

    @SerializedName("cloudCover")
    var cloudCover: Double = 0.toDouble()

    @SerializedName("temperatureMin")
    var temperatureMin: Double = 0.toDouble()

    @SerializedName("apparentTemperatureHighTime")
    var apparentTemperatureHighTime: Int = 0

    @SerializedName("pressure")
    var pressure: Double = 0.toDouble()

    @SerializedName("dewPoint")
    var dewPoint: Double = 0.toDouble()

    @SerializedName("temperatureMinTime")
    var temperatureMinTime: Int = 0

    @SerializedName("uvIndexTime")
    var uvIndexTime: Int = 0

    @SerializedName("apparentTemperatureMax")
    var apparentTemperatureMax: Double = 0.toDouble()

    @SerializedName("temperatureHigh")
    var temperatureHigh: Double = 0.toDouble()

    @SerializedName("temperatureLowTime")
    var temperatureLowTime: Int = 0

    @SerializedName("apparentTemperatureHigh")
    var apparentTemperatureHigh: Double = 0.toDouble()

    @SerializedName("time")
    var time: Int = 0

    @SerializedName("precipIntensityMaxTime")
    var precipIntensityMaxTime: Int = 0

    @SerializedName("windGustTime")
    var windGustTime: Int = 0

    @SerializedName("uvIndex")
    var uvIndex: Int = 0

    @SerializedName("apparentTemperatureMaxTime")
    var apparentTemperatureMaxTime: Int = 0

    override fun toString(): String {
        return "DataItem{" +
                "windGust = '" + windGust + '\''.toString() +
                ",apparentTemperatureMinTime = '" + apparentTemperatureMinTime + '\''.toString() +
                ",temperatureMax = '" + temperatureMax + '\''.toString() +
                ",icon = '" + icon + '\''.toString() +
                ",precipIntensityMax = '" + precipIntensityMax + '\''.toString() +
                ",windBearing = '" + windBearing + '\''.toString() +
                ",ozone = '" + ozone + '\''.toString() +
                ",temperatureMaxTime = '" + temperatureMaxTime + '\''.toString() +
                ",apparentTemperatureMin = '" + apparentTemperatureMin + '\''.toString() +
                ",sunsetTime = '" + sunsetTime + '\''.toString() +
                ",temperatureLow = '" + temperatureLow + '\''.toString() +
                ",precipType = '" + precipType + '\''.toString() +
                ",humidity = '" + humidity + '\''.toString() +
                ",moonPhase = '" + moonPhase + '\''.toString() +
                ",windSpeed = '" + windSpeed + '\''.toString() +
                ",apparentTemperatureLowTime = '" + apparentTemperatureLowTime + '\''.toString() +
                ",sunriseTime = '" + sunriseTime + '\''.toString() +
                ",apparentTemperatureLow = '" + apparentTemperatureLow + '\''.toString() +
                ",summary = '" + summary + '\''.toString() +
                ",precipProbability = '" + precipProbability + '\''.toString() +
                ",temperatureHighTime = '" + temperatureHighTime + '\''.toString() +
                ",visibility = '" + visibility + '\''.toString() +
                ",precipIntensity = '" + precipIntensity + '\''.toString() +
                ",cloudCover = '" + cloudCover + '\''.toString() +
                ",temperatureMin = '" + temperatureMin + '\''.toString() +
                ",apparentTemperatureHighTime = '" + apparentTemperatureHighTime + '\''.toString() +
                ",pressure = '" + pressure + '\''.toString() +
                ",dewPoint = '" + dewPoint + '\''.toString() +
                ",temperatureMinTime = '" + temperatureMinTime + '\''.toString() +
                ",uvIndexTime = '" + uvIndexTime + '\''.toString() +
                ",apparentTemperatureMax = '" + apparentTemperatureMax + '\''.toString() +
                ",temperatureHigh = '" + temperatureHigh + '\''.toString() +
                ",temperatureLowTime = '" + temperatureLowTime + '\''.toString() +
                ",apparentTemperatureHigh = '" + apparentTemperatureHigh + '\''.toString() +
                ",time = '" + time + '\''.toString() +
                ",precipIntensityMaxTime = '" + precipIntensityMaxTime + '\''.toString() +
                ",windGustTime = '" + windGustTime + '\''.toString() +
                ",uvIndex = '" + uvIndex + '\''.toString() +
                ",apparentTemperatureMaxTime = '" + apparentTemperatureMaxTime + '\''.toString() +
                "}"
    }
}