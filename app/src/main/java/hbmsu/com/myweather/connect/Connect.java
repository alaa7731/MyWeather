package hbmsu.com.myweather.connect;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import hbmsu.com.myweather.R;
import hbmsu.com.myweather.Utils;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by asalah on 11/6/2017.
 */

public class Connect implements Callback {

    public static com.johnhiott.darkskyandroidlib.models.Request.Units unitSi = com.johnhiott.darkskyandroidlib.models.Request.Units.SI;
    public static com.johnhiott.darkskyandroidlib.models.Request.Units unitFr = com.johnhiott.darkskyandroidlib.models.Request.Units.US;
    public com.johnhiott.darkskyandroidlib.models.Request.Units selectedUnit = com.johnhiott.darkskyandroidlib.models.Request.Units.SI;
    private static final Connect ourInstance = new Connect();
    private String BASE_URL = "https://api.darksky.net/forecast/e551edd89cf2e1ce3b92533b222b330c/";
    private WeatherServices weatherServices;
    private Context context;
    private static final String TAG = "Connect";
    private OnResponse<JSONObject> onResponse;

    public static Connect getInstance() {
        return ourInstance;
    }

    public void init(Context context) {
        this.context = context;
    }


    private Connect() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                try {
                    if (Utils.isConnectingToInternet(context)) {
                        Request original = chain.request();
                        Request request = null;
//                        if (!sessionManagement.isLoggedIn())
//                            request = original.newBuilder().tag(new Object[]{returnType, onResponse})
//                                    .header("Content-Type", "application/json")
//                                    .header("Accept", "application/json")
//                                    .method(original.method(), original.body())
//                                    .build();
//                        else
                        request = original.newBuilder()
//                                .header("Content-Type", "application/json")
//                                .header("Accept", "application/json")
                                .method(original.method(), original.body())
                                .build();
                        bodyToString(request);

                        try {

                            ((Activity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showLoading();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return chain.proceed(request);
                    } else {
                        throw new IOException(context.getString(R.string.internetMessage));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return chain.proceed(chain.request());
                }
            }
        });

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        weatherServices = retrofit.create(WeatherServices.class);

    }

    private static void bodyToString(final Request request) {

        try {
            Log.e(TAG, "headers: " + request.headers().toString());

            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            Log.e(TAG, "Function: " + request.url().url());
//            copy.body().writeTo(buffer);

            Log.e(TAG, "bodyToString: " + buffer.readUtf8());
        } catch (Exception e) {
            Log.e(TAG, "IOException: " + e.getLocalizedMessage());
        }
    }


    public void showLoading() {
        try {
            Utils.showLoading(context);
        } catch (Exception e) {
        }

    }


    @Override
    public void onResponse(Call call, final Response response) {
        JSONObject jsonObject = (JSONObject) response.body();
        if (jsonObject != null) {
            Log.e(TAG, "onResponse: " + jsonObject.toString());
            onResponse.onSuccess(jsonObject);
        } else {
//            showError(null);

        }
        hideLoading();

    }


    @Override
    public void onFailure(Call call, Throwable t) {
        if (context != null) {
            hideLoading();
            Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
        }
    }

    private void hideLoading() {

        Utils.hideLoading();

    }

//    private void showError(String string) {
//        if (context != null) {
//
//            if (TextUtils.isEmpty(string))
//                string = context.getString(R.string.server_error);
//        }
//    }


    public void getWeather(com.johnhiott.darkskyandroidlib.models.Request.Units unit, OnResponse<JSONObject> onResponse) {
        String unitStr = unit == Connect.unitFr ? "si" : "us";
        weatherServices.getWeather("minutely", unitStr).enqueue(this);
        this.onResponse = onResponse;
    }

}
