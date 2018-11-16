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
    public ForecastInstance makeCall(WeatherDatabase dBinstance) {
        ForecastInstance forecastInstance = new ForecastInstance();
        BufferedReader in = null;
        try {
            URL url = new URL(URL_DEF);
            URLConnection urlConnection = url.openConnection();

            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            Gson gson = new Gson();
            Response response = gson.fromJson(in, Response.class);
            forecastInstance.setSearchTime(response.getApprovedTime());
            dBinstance.daoAccess().insertFCInstance(forecastInstance);
            int fCId = dBinstance.daoAccess().getAllForcasts().getId();
            TimeSeriesInstance timeSeriesInstance = new TimeSeriesInstance();
            List<TimeSeriesInstance> timeSeriesInstances = new ArrayList<>();

            //TODO create temp, cc, time instances to add to list of pojos.
            for (int i = 0; i < response.getTimeSeries().size(); i++) {
                timeSeriesInstance.setTimeForValues(response.getTimeSeries().get(i).getValidTime());
                for (int j = 0; j < response.getTimeSeries().get(j).getParameters().size(); j++) {
                    String name = response.getTimeSeries().get(j).getParameters().get(j).getName();
                    if (name.equals("t")) {
                        timeSeriesInstance.setTemperature(response.getTimeSeries().get(j).getParameters().get(j).getValues().get(0));
                        //TODO CREATE POJO FOR TRANSFER TO DB
                    } else if(name.equals("tcc_mean")){
                        timeSeriesInstance.setCloudCoverage(response.getTimeSeries().get(j).getParameters().get(j).getValues().get(0));
                    }
                }
                timeSeriesInstances.add(timeSeriesInstance);
            }
            forecastInstance.setTimeSeriesInstances(timeSeriesInstances);
            forecastInstance.setSearchTime(response.getApprovedTime());
            return forecastInstance;
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
        return forecastInstance;
    }
}
