package sunny.com.wethrapp.Controller;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpHandler {

    private String makeRequest(String urlAddress)   {
        HttpURLConnection http = null;
        String response = null;
        try {
            URL url = new URL(urlAddress);
            http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");

            //Get String from stream
            Reader reader = new InputStreamReader(http.getInputStream());

            //Gson from reader
            Gson gson = new Gson();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private String streamToString(InputStream inputStream){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer sb = new StringBuffer();
        String row;
        try {
            while((row = bufferedReader.readLine()) != null){
                sb.append(row).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /*private JSONObject convertStringToJsonObj (String response){
        *//*ForcastInstance forcastInstance = new ForcastInstance();
        if(response != null){
            try{
                Gson gson = new Gson();
                *//**//*gson.fr(response);
                String approvedTime = (String) jsonObject.get("approvedTime");*//**//*


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }*//*
    }*/

 /*   private Date getDateFromString(String stringDate){
        if(stringDate != null){

        }
    }*/
}
