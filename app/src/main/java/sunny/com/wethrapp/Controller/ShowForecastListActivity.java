package sunny.com.wethrapp.Controller;

import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import sunny.com.wethrapp.Controller.parser.HttpHandler;
import sunny.com.wethrapp.R;
import sunny.com.wethrapp.Controller.parser.Response;
import sunny.com.wethrapp.model.DB.entity.Converters;
import sunny.com.wethrapp.model.DB.entity.ForecastInstance;
import sunny.com.wethrapp.model.DB.entity.TimeSeriesInstance;
import sunny.com.wethrapp.model.DaoAccess;
import sunny.com.wethrapp.model.WeatherDatabase;
import sunny.com.wethrapp.view.ForecastAdaptor;

import static sunny.com.wethrapp.model.DB.entity.Converters.fromStringToDate;

public class ShowForecastListActivity extends AppCompatActivity {

    protected List<TimeSeriesInstance> timeSeriesInstancesList;
    private static String lon = null;
    private static String lat = null;
    protected RecyclerView recycler_view;
    private static final String TAG = "LogAppTest";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_forecast_list);
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            lon = bundle.getString("lon");
            lat = bundle.getString("lat");
        }
        Log.d("longlat", "val " + lon + " val2 " + lat);

        UpdateListAsynkTask workerThread = new UpdateListAsynkTask(lat, lon, WeatherDatabase.getInstance(getApplicationContext()).daoAccess());
        workerThread.execute();

        initElements();
    }

    private void initElements() {
        recycler_view = (RecyclerView) findViewById(R.id.recycle_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setHasFixedSize(true);
    }


    public class UpdateListAsynkTask extends AsyncTask<Void, Void, List<TimeSeriesInstance>> {
        private Response response;
        String lat, lon;
        private static final String TAG = "LogAppTest";
        private DaoAccess forecastDao;
        private ForecastInstance forecastInstance;
        private TimeSeriesInstance timeSeriesInstance;

        public UpdateListAsynkTask(String lat, String lon, DaoAccess forecastDao) {
            this.lat = lat;
            this.lon = lon;
            this.forecastDao = forecastDao;
            this.forecastInstance = new ForecastInstance();
            this.timeSeriesInstance = new TimeSeriesInstance();
        }

        public TimeSeriesInstance getTimeSeriesInstance() {
            return timeSeriesInstance;
        }

        public ForecastInstance getForecastInstance() {
            return forecastInstance;
        }

        @Override
        protected List<TimeSeriesInstance> doInBackground(Void... voids) {
            Log.d(TAG, "in async task Make Call - LAT: " + lat + " LON: " + lon);
            HttpHandler httpHandler = new HttpHandler();
            this.response = httpHandler.makeCall(lon, lat);
            if (response != null) {
                Log.d(TAG, "smhirepo - response not null");
            } else {
                Log.d(TAG, "smhirepo - response null");
            }

            try {
                String name;
                forecastInstance = new ForecastInstance();
                timeSeriesInstance = new TimeSeriesInstance();
                ArrayList<TimeSeriesInstance> timeSeriesInstances = new ArrayList<>();
                Response.TimeSeriesBean.ParametersBean parametersBean;
                String stringDate = response.getApprovedTime();

                double longitude = response.getGeometry().getCoordinates().get(0).get(0);
                double latitude = response.getGeometry().getCoordinates().get(0).get(1);
                forecastInstance.setLongitude(longitude);
                forecastInstance.setLatitude(latitude);

                forecastInstance.setSearchTime(fromStringToDate(stringDate));
                forecastDao.insertFCInstance(forecastInstance);

                Log.d(TAG, forecastInstance.getSearchTime().toString());

                for (int i = 0; i < response.getTimeSeries().size(); i++) {
                    //Add time of measure
                    Date dateFormat = null;
                    stringDate = response.getTimeSeries().get(i).getValidTime();

                    dateFormat = fromStringToDate(stringDate);

                    Log.d(TAG, "\ndateformat in converting response to pojo" + dateFormat);
                    timeSeriesInstance.setTimeForValues(dateFormat);
                    for (int j = 0; j < response.getTimeSeries().get(i).getParameters().size(); j++) {
                        parametersBean = response.getTimeSeries().get(i).getParameters().get(j);
                        name = response.getTimeSeries().get(i).getParameters().get(j).getName();
                        if (name.equals("t")) { // temperature
                            timeSeriesInstance.setTemperature(parametersBean.getValues().get(0));
                        } else if (name.equals("tcc_mean")) { // cc
                            timeSeriesInstance.setCloudCoverage(parametersBean.getValues().get(0));
                        }
                    }
                    timeSeriesInstances.add(timeSeriesInstance);
                    timeSeriesInstance = new TimeSeriesInstance();
                }
                forecastDao.insertAllTimeSeries(timeSeriesInstances);
                Log.d(TAG, "response and add to db successful");
                return timeSeriesInstances;
            } catch (Exception e) {
                Log.d(TAG, "error when inserting");
            }
            Log.d(TAG, "Returning new ARRAY from response");
            return new ArrayList<>();
        }

        @Override
        protected void onPostExecute(List<TimeSeriesInstance> ts) {
            ForecastAdaptor adaptor = new ForecastAdaptor(ts);
            timeSeriesInstancesList = ts;
            adaptor.setTimeSeriesInstanceList(ts);
            recycler_view.setAdapter(adaptor);

        }
        private Date convertStringToDate(String stringDate){
            Date date = null;
            stringDate = stringDate.replace("T", "-");
            stringDate = stringDate.replace("Z", "-");
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD-HH:mm", Locale.GERMAN);
            Log.d(TAG, "String result: " + sdf);
            try {
                date = sdf.parse(stringDate);
            } catch (ParseException e) {
                Log.d(TAG, "Conversion error in ConvertStringToDate");
                e.printStackTrace();
            }
            return date == null ? new Date() : date;
        }
    }
}
