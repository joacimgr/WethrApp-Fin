package sunny.com.wethrapp.Controller.parser;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import sunny.com.wethrapp.model.DB.entity.ForecastInstance;
import sunny.com.wethrapp.model.DaoAccess;
import sunny.com.wethrapp.model.WeatherDatabase;

public class HttpHandler {

    private static final String URL_DEF = "https://maceo.sth.kth.se/api/category/pmp3g/version/2/geotype/point/lon/%/lat/¤/";
    private static final String TAG = "LogAppTest";
    /**
     * Constructor empty
     */
    public HttpHandler() {
    }

    /**
     * This method will attempt to make a REST call to
     * smhi's API with lat and long
     *
     * @return
     */
    public Response makeCall(String lon, String lat) {
        BufferedReader in = null;
        Response response = null;
        String completeUrl = replaceLatLonOnURL(lat, lon);
        try {
            URL url = new URL(completeUrl);
            URLConnection urlConnection = url.openConnection();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            Gson gson = new Gson();
            response = gson.fromJson(in, Response.class);
        } catch (MalformedURLException e) {
            Log.d(TAG, "Make call http error IOex");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(TAG, "Make call http error IOex");
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }

    private String replaceLatLonOnURL(String lat, String lon){
        String returnString = URL_DEF;
        returnString = returnString.replace("%", lon);
        returnString = returnString.replace("¤", lat);
        Log.d(TAG, returnString);
        return returnString;
    }
}
