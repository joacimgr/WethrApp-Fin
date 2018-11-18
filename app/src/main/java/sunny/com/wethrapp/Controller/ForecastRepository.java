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


    /*private static class updateAllForcastsAsynkTask extends AsyncTask<Response, Void, Void>{
        private DaoAccess forecastDao;
        private ForecastInstance forecast;
        private Response response;


        public updateAllForcastsAsynkTask(DaoAccess forecastDao, Response response) {
            this.forecastDao = forecastDao;
            this.response = response;
        }

        @Override
        protected Void doInBackground(Response... responses) {

            try{
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
                //dateFormat = Converters.fromStringToDate(stringDate);
                forecastInstance.setSearchTime(stringDate);
                forecastDao.insertFCInstance(forecastInstance);
                Log.d(TAG, forecastInstance.getSearchTime());
                // populate list of values from result to timeseries objects
                for (int i = 0; i < response.getTimeSeries().size(); i++) {
                    //Add time of measure
                    stringDate = response.getTimeSeries().get(i).getValidTime();
                    //dateFormat = Converters.fromStringToDate(stringDate);
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
            }catch (Exception e){
                Log.d("ForecastRepository", "error when inserting");
            }
            return null;
        }
    }*/
}
