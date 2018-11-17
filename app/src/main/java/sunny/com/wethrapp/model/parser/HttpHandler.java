package sunny.com.wethrapp.model.parser;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import sunny.com.wethrapp.model.DB.entity.ForecastInstance;
import sunny.com.wethrapp.model.DB.entity.TimeSeriesInstance;
import sunny.com.wethrapp.model.WeatherDatabase;

public class HttpHandler {

    private static final String URL_DEF = "https://maceo.sth.kth.se/api/category/pmp3g/version/2/geotype/point/lon/14.333/lat/60.383/";

    /**
     * Constructor empty
     */
    public HttpHandler() {
    }

    /**
     * This method will att
     *
     * @return
     */
    public Response makeCall(WeatherDatabase dBinstance) {
        ForecastInstance forecastInstance = new ForecastInstance();
        BufferedReader in = null;
        Response response = null;
        try {
            URL url = new URL(URL_DEF);
            URLConnection urlConnection = url.openConnection();

            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            Gson gson = new Gson();
            response = gson.fromJson(in, Response.class);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
}
