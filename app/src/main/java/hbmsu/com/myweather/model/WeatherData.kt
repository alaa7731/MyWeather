package hbmsu.com.myweather.model

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

import java.io.Serializable

@Generated("com.asif.gsonpojogenerator")
class WeatherData : Serializable {

    @SerializedName("currently")
    var currently: Currently? = null

    @SerializedName("offset")
    var offset: Int = 0

    @SerializedName("timezone")
    var timezone: String? = null

    @SerializedName("latitude")
    var latitude: Double = 0.toDouble()

    @SerializedName("daily")
    var daily: Daily? = null

    @SerializedName("hourly")
    var hourly: Hourly? = null

    @SerializedName("longitude")
    var longitude: Double = 0.toDouble()

    override fun toString(): String {
        return "WeatherData{" +
                "currently = '" + currently + '\''.toString() +
                ",offset = '" + offset + '\''.toString() +
                ",timezone = '" + timezone + '\''.toString() +
                ",latitude = '" + latitude + '\''.toString() +
                ",daily = '" + daily + '\''.toString() +
                ",hourly = '" + hourly + '\''.toString() +
                ",longitude = '" + longitude + '\''.toString() +
                "}"
    }
}