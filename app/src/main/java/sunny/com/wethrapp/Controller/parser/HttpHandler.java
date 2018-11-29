package sunny.com.wethrapp.Controller.parser;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles requests and returns results
 */
public class HttpHandler {

    private static final String URL_DEF = "https://maceo.sth.kth.se/api/category/pmp3g/version/2/geotype/point/lon/{lon}/lat/{lat}/";
    private static final String URL_DEF_SMHI_POINT = "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/{lon}/lat/{lat}/data.json ";

    private static final String URL_LOC_DEF = "http://smhi.se/wpt-a/backend_solr/autocomplete/search/{place}";
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

    /**
     * Attempts to fetch location information from SMHI
     * @param place
     * @return
     */
    public List<LocationResponse> makeCallLocation(String place) {
        BufferedReader in = null;
        String completeUrl = replacePlaceOnURL(place);
        try {
            URL url = new URL(completeUrl);
            URLConnection urlConnection = url.openConnection();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            Type collectionType = new TypeToken<List<LocationResponse>>(){}.getType();
            List<LocationResponse> response = (List<LocationResponse>) new Gson().fromJson( in , collectionType);
            if(response != null){
                return response;
            }
        } catch (MalformedURLException e) {
            Log.d(TAG, "Make call http URL");
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
        return new ArrayList<>();
    }

    /**
     * Replace {place} on URL_LOC_DEF (see class javadoc) with a parameter.
     * This to create a dynamic call with user interaction on what to get.
     * @param place
     * @return
     */
    private String replacePlaceOnURL(String place) {
        String returnString = URL_LOC_DEF;
        returnString = returnString.replace("{place}", place);
        Log.d(this.getClass().getSimpleName() + TAG, "place from replacePlaceOnURL: " + returnString);
        return returnString;
    }

    /**
     * Replaces {lon} with longitude and {lat} with lat parameter.
     * This to create a dynamic call with user interaction on what to get.
     * @param lat
     * @param lon
     * @return
     */
    private String replaceLatLonOnURL(String lat, String lon){
        String returnString = URL_DEF_SMHI_POINT;
        returnString = returnString.replace("{lon}", lon);
        returnString = returnString.replace("{lat}", lat);
        Log.d(TAG, returnString);
        return returnString;
    }
}
