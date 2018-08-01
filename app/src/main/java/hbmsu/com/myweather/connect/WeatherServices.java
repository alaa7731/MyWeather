package hbmsu.com.myweather.connect;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by asalah on 11/6/2017.
 */

public interface WeatherServices {

    @GET("25.0750853,54.947555")
    Call<JSONObject> getWeather(@Query("exclude") String exclude, @Query("unit") String unit);


}
