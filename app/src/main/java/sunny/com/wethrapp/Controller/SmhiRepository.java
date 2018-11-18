package sunny.com.wethrapp.Controller;


import android.app.Application;
import android.os.AsyncTask;

import sunny.com.wethrapp.Controller.parser.HttpHandler;
import sunny.com.wethrapp.Controller.parser.Response;
import sunny.com.wethrapp.model.DaoAccess;
import sunny.com.wethrapp.model.WeatherDatabase;

public class SmhiRepository {

    private DaoAccess forecastDao;
    private Response response;

    public SmhiRepository(Application application) {
        WeatherDatabase weatherDatabase = WeatherDatabase.getInstance(application);
        forecastDao = weatherDatabase.daoAccess();
    }


    public void makeCall(String lat, String lon){
        new MakeCallAsyncTask(forecastDao, lat, lon).execute();
    }

    public Response getResponse() {
        return response;
    }

    /**
     * This method deletes all forecasts from the db
     */
    private static class MakeCallAsyncTask extends AsyncTask<Void, Void, Response> {
        private DaoAccess forecastDao;
        private Response response;
        String lat, lon;

        private MakeCallAsyncTask(DaoAccess daoAccess, String lat, String lon){
            this.forecastDao = daoAccess;
            this.lat = lat;
            this.lon = lon;
        }

        @Override
        protected Response doInBackground(Void... voids) {
            HttpHandler httpHandler = new HttpHandler();
            this.response = httpHandler.makeCall(lon, lat);
            return response;
        }
    }
}