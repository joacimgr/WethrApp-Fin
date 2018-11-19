package sunny.com.wethrapp.Controller;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sunny.com.wethrapp.Controller.parser.HttpHandler;
import sunny.com.wethrapp.model.DB.entity.Converters;
import sunny.com.wethrapp.model.DB.entity.ForecastInstance;
import sunny.com.wethrapp.model.DB.entity.TimeSeriesInstance;
import sunny.com.wethrapp.model.DaoAccess;
import sunny.com.wethrapp.model.WeatherDatabase;
import sunny.com.wethrapp.Controller.parser.Response;

public class ForecastRepository {

    private DaoAccess forecastDao;
    private List<TimeSeriesInstance> allForecasts;
    private Response response;
    private static final String TAG = "LogAppTest";
    public ForecastRepository (Application application, Response response){
        WeatherDatabase weatherDatabase = WeatherDatabase.getInstance(application);
        forecastDao = weatherDatabase.daoAccess();
        allForecasts = forecastDao.getAllTimeSeries();
        this.response = response;
    }

    /*public void updateForcasts(){
        new updateAllForcastsAsynkTask(forecastDao, response).execute();
    }*/

    /**
     *
     */
    public void deleteAllForecasts(){
        new deletAllForcastsAsyncTask(forecastDao).execute();
    }

    /**
     *
     * @return
     */
    public List<TimeSeriesInstance> getAllForecasts() {
        return allForecasts;
    }

    /**
     * This method deletes all forecasts from the db
     */
    private static class deletAllForcastsAsyncTask extends AsyncTask<Void, Void, Void>{
        private DaoAccess forecastDao;

        private deletAllForcastsAsyncTask(DaoAccess daoAccess){
            this.forecastDao = daoAccess;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            forecastDao.deleteAllFromTimeseries();
            return null;
        }
    }
}
