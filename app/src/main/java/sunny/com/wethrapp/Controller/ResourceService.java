package sunny.com.wethrapp.Controller;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.List;

import sunny.com.wethrapp.model.DB.entity.ForecastInstance;
import sunny.com.wethrapp.model.DB.entity.TimeSeriesInstance;
import sunny.com.wethrapp.model.WeatherDatabase;
import sunny.com.wethrapp.model.parser.HttpHandler;
import sunny.com.wethrapp.model.parser.Response;

public class ResourceService extends Service {
    WeatherDatabase dBinstance;
    private static final String url = "https://maceo.sth.kth.se/api/category/pmp3g/version/2/geotype/point/lon/14.333/lat/60.383/";
    private HttpHandler httpHandler;
    private List<TimeSeriesInstance> timeSeriesInstances;
    private TimeSeriesInstance timeSeriesInstance;
    private ForecastInstance forecastInstance;
    private Response response;

    public ResourceService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        init();
        new Thread(() -> {
            response = httpHandler.makeCall(dBinstance);
            insertIntoDatabase(response);
        });
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void init(){
        httpHandler = new HttpHandler();
        dBinstance = WeatherDatabase.getInstance(this);
        timeSeriesInstances = new ArrayList<>();
        timeSeriesInstance = new TimeSeriesInstance();
        forecastInstance = new ForecastInstance();
    }

    private void insertIntoDatabase(Response response){

        dBinstance.daoAccess().insertFCInstance(forecastInstance);
        int fCId = dBinstance.daoAccess().getAllForcasts().getId();
        forecastInstance.setSearchTime(response.getApprovedTime());

        for (int i = 0; i < response.getTimeSeries().size(); i++) {
            timeSeriesInstance.setTimeForValues(response.getTimeSeries().get(i).getValidTime());
            for (int j = 0; j < response.getTimeSeries().get(j).getParameters().size(); j++) {
                Response.TimeSeriesBean.ParametersBean parametersBean =
                        response.getTimeSeries().get(j).getParameters().get(j);
                String name = response.getTimeSeries().get(j).getParameters().get(j).getName();
                if (parametersBean.getName().equals("t")) {
                    timeSeriesInstance.setTemperature(parametersBean.getValues().get(0));
                } else if(name.equals("tcc_mean")){
                    timeSeriesInstance.setCloudCoverage(parametersBean.getValues().get(0));
                }
            }
            timeSeriesInstances.add(timeSeriesInstance);
        }
        forecastInstance.setSearchTime(response.getApprovedTime());
        dBinstance.daoAccess().insertAllTimeseries(timeSeriesInstances);

    }







}
