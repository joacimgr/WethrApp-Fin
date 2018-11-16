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
    public HttpHandler() {}

    /**
     * This method will att
     * @return
     */
    private ForcastInstance makeCall(){
        ForcastInstance forcastInstance = new ForcastInstance();
        BufferedReader  in = null;
        try {
            URL url = new URL(URL_DEF);
            URLConnection urlConnection = url.openConnection();

            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            ForcastValues values = new ForcastValues();
            ArrayList<ForcastValues> valuesArrayList = new ArrayList<>();
            Gson gson = new Gson();
            ConverterObjects.Response response = gson.fromJson(in, ConverterObjects.Response.class);
            int i = 0;
            for (ConverterObjects.TimeSeries timeSeries: response.getTimeSeries()) {
                for (ConverterObjects.Parameter parameter: timeSeries.getParameter()) {
                    values.setLevel(parameter.getLevel());
                    values.setName(parameter.getName());
                    values.setUnit(parameter.getUnit());
                    values.setValue(parameter.getValues()[0]);
                }
                valuesArrayList.add(values);
            }
            Double[] coords = null;
            coords = response.getGeometry().getCoordinates()[0].getCoorinates();
            forcastInstance.setValues(valuesArrayList);
            forcastInstance.setCoorinates(coords);
            forcastInstance.setApprovedTime(response.getApprovedTime());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(in != null){
                try{
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return forcastInstance;
    }
}
