package hbmsu.com.myweather.connect;

import android.support.annotation.Nullable;

/**
 * Created by asalah on 11/7/2017.
 */

public interface OnResponse<T> {

    void onSuccess(@Nullable T data);

 }
