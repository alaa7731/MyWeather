package hbmsu.com.myweather;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import hbmsu.com.myweather.connect.Connect;

/**
 * Created by asalah on 11/6/2017.
 */

public class Utils {

    public static boolean isConnectingToInternet(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        Log.w("INTERNET:", String.valueOf(i));
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            Log.w("INTERNET:", "connected!");
                            return true;
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }

    public static void clear(View v) {
        ViewCompat.setAlpha(v, 1);
        ViewCompat.setScaleY(v, 1);
        ViewCompat.setScaleX(v, 1);
        ViewCompat.setTranslationY(v, 0);
        ViewCompat.setTranslationX(v, 0);
        ViewCompat.setRotation(v, 0);
        ViewCompat.setRotationY(v, 0);
        ViewCompat.setRotationX(v, 0);
        ViewCompat.setPivotY(v, v.getMeasuredHeight() / 2);
        ViewCompat.setPivotX(v, v.getMeasuredWidth() / 2);
        ViewCompat.animate(v).setInterpolator(null).setStartDelay(0);
    }

    public static ProgressDialog progressBar;

    public static void showLoading(@NotNull Context context) {

        try {
            if (progressBar != null) {
                return;
            }
            progressBar = new ProgressDialog(context, R.style.loading_dialog_style);
            progressBar.setIndeterminate(false);
            progressBar.setMessage("Loading...");
            progressBar.show();

        } catch (Exception e) {
        }


    }

    public static void hideLoading() {
        try {
            if (progressBar != null) {
                progressBar.dismiss();
            }
            progressBar = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object copy(Object orig) {
        Object obj = null;
        try {
            // Write the object out to a byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(orig);
            out.flush();
            out.close();

            // Make an input stream from the byte array and read
            // a copy of the object back in.
            ObjectInputStream in = new ObjectInputStream(
                    new ByteArrayInputStream(bos.toByteArray()));
            obj = in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        return obj;
    }

    public static String formatMilliToTime(int timeInMilli) {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a", Locale.US);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMilli * 1000);
        return formatter.format(calendar.getTime());

    }

    public static String formatMilliToDateTime(int timeInMilli) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.US);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMilli * 1000);
        return formatter.format(calendar.getTime());

    }

    public static String formatMilliToDate(long timeInMilli) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy EEE", Locale.US);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMilli * 1000);
        return formatter.format(calendar.getTime());

    }

    public static String getDegreeUnit() {
        return Connect.getInstance().selectedUnit.equals(Connect.unitFr) ? "F" : "C";
    }

    public static String getVisibility(String visibility) {
        try {
            return Connect.getInstance().selectedUnit.equals(Connect.unitFr) ?
                    visibility + " Mile" :
                    (int) (Double.parseDouble(visibility) * 1.60934) + " Km";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return visibility + " Mile";

    }

    public static <T> List<T> getGsonList(String response, Class<T> convertTo) {

        try {
            Gson gson = new Gson();
            List<T> listObjects = new ArrayList<>();

            //read each object of array with Json library
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {

                //get the object
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                //get string of object from Json library to convert it to real object with Gson library
                listObjects.add(Utils.getModel(jsonObject.toString(), convertTo));
            }

            //return list with all generated objects
            return listObjects;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T getModel(JSONObject response, Class<T> classToConvert) {
        return new Gson().fromJson(response.toString(), classToConvert);
    }

    public static <T> T getModel(String response, Class<T> classToConvert) {
        return new Gson().fromJson(response, classToConvert);
    }

    public static String getStringFromModel(Object model) {
        return new Gson().toJson(model);
    }
}
