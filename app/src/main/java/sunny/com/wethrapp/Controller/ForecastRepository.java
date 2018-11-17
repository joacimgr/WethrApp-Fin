package sunny.com.wethrapp.Controller;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import sunny.com.wethrapp.model.DB.entity.Converters;
import sunny.com.wethrapp.model.DB.entity.ForecastInstance;
import sunny.com.wethrapp.model.DB.entity.TimeSeriesInstance;
import sunny.com.wethrapp.model.DaoAccess;
import sunny.com.wethrapp.model.WeatherDatabase;
import sunny.com.wethrapp.model.parser.Response;

public class ForecastRepository {

    private DaoAccess forecastDao;
    private LiveData<List<TimeSeriesInstance>> allForecasts;

    public ForecastRepository (Application application){
        WeatherDatabase weatherDatabase = WeatherDatabase.getInstance(application);
        forecastDao = weatherDatabase.daoAccess();
        allForecasts = forecastDao.getAllTimeSeries();
    }

    public void updateForcasts(Response response){
        new updateAllForcastsAsynkTask(forecastDao, response).execute();
    }

    public void deleteAllForecasts(){
        new deletAllForcastsAsyncTask(forecastDao).execute();
    }

    public LiveData<List<TimeSeriesInstance>> getAllForecasts() {
        return allForecasts;
    }

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

    private void insertIntoDatabase(Response response){
        String name;
        ForecastInstance forecastInstance = new ForecastInstance();
        TimeSeriesInstance timeSeriesInstance = new TimeSeriesInstance();
        ArrayList<TimeSeriesInstance> timeSeriesInstances = new ArrayList<>();
        Response.TimeSeriesBean.ParametersBean parametersBean;
        String stringDate = response.getApprovedTime();
        Date dateFormat = null;

        double longitude = response.getGeometry().getCoordinates().get(0).get(0);
        double latitude = response.getGeometry().getCoordinates().get(0).get(1);
        forecastInstance.setLongitude(longitude);
        forecastInstance.setLatitude(latitude);
        dateFormat = Converters.fromStringToDate(stringDate);
        forecastInstance.setSearchTime(dateFormat);
        forecastDao.insertFCInstance(forecastInstance);

        // populate list of values from result to timeseries objects
        for (int i = 0; i < response.getTimeSeries().size(); i++) {
            //Add time of measure
            stringDate = response.getTimeSeries().get(i).getValidTime();
            timeSeriesInstance.setTimeForValues(stringDate);
            for (int j = 0; j < response.getTimeSeries().get(i).getParameters().size(); j++) {
                parametersBean = response.getTimeSeries().get(i).getParameters().get(j);
                name = response.getTimeSeries().get(i).getParameters().get(j).getName();
                if (name.equals("t")) { // temperature
                    timeSeriesInstance.setTemperature(parametersBean.getValues().get(0));
                } else if(name.equals("tcc_mean")){ // cc
                    timeSeriesInstance.setCloudCoverage(parametersBean.getValues().get(0));
                }
            }
            timeSeriesInstances.add(timeSeriesInstance);
            timeSeriesInstance = new TimeSeriesInstance();
        }
        forecastDao.insertAllTimeSeries(timeSeriesInstances);
    }

    private class updateAllForcastsAsynkTask extends AsyncTask<Response, Void, Void>{
        private DaoAccess forecastDao;
        private ForecastInstance forecast;
        public updateAllForcastsAsynkTask(DaoAccess forecastDao, Response response) {
            this.forecastDao = forecastDao;
        }

        @Override
        protected Void doInBackground(Response... responses) {
            forecast = forecastDao.getForcast();
            if(forecast != null){
            Date date = forecast.getSearchTime();

            }
            return null;
        }
    }
}
