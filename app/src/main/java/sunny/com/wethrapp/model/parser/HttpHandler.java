package sunny.com.wethrapp.model.parser;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import sunny.com.wethrapp.model.DB.entity.ForcastInstance;
import sunny.com.wethrapp.model.DB.entity.ForcastValues;
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
    public ForcastInstance makeCall(WeatherDatabase dBinstance) {
        ForcastInstance forcastInstance = new ForcastInstance();
        BufferedReader in = null;
        try {
            URL url = new URL(URL_DEF);
            URLConnection urlConnection = url.openConnection();

            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            Gson gson = new Gson();
            Response response = gson.fromJson(in, Response.class);
            forcastInstance.setSearchTime(response.getApprovedTime());
            dBinstance.daoAccess().insertFCInstance(forcastInstance);
            dBinstance.daoAccess().getForCastInstanceById();
            
            //TODO create temp, cc, time instances to add to list of pojos.
            for(int i = 0; i < response.getTimeSeries().size() ; i++){
                for(int j = 0; j < response.getTimeSeries().get(j).getParameters().size(); j++){
                    if (response.getTimeSeries().get(j).getParameters().get(j).getName().equals("t")) {
                        Response.TimeSeriesBean.ParametersBean pbean = response.getTimeSeries().get(j).getParameters().get(j);
                        //TODO CREATE POJO FOR TRANSFER TO DB
                    }
                }
            }


            forcastInstance.setSearchTime(response.getApprovedTime());

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
        return forcastInstance;
    }
}
