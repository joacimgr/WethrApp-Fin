package sunny.com.wethrapp.Controller;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.Minutes;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import sunny.com.wethrapp.Controller.parser.HttpHandler;
import sunny.com.wethrapp.R;
import sunny.com.wethrapp.Controller.parser.Response;
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
    private TextView searchParamView, dateTextView;
    private String searchParam;
    private static final String TAG = " LogAppTest";
    private WeatherDatabase weatherDatabase;
    private Context context;

    @Override
    protected void onDestroy() {
        weatherDatabase.daoAccess().deleteAllFromTimeseries();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_forecast_list);
        Bundle bundle = getIntent().getExtras();
        context = getApplicationContext();
        weatherDatabase = WeatherDatabase.getInstance(context);

        if (bundle != null) {
            lon = bundle.getString("lon");
            lat = bundle.getString("lat");
        }

        Log.d(this.getClass().getSimpleName() + TAG, "val " + lon + " val2 " + lat);
        StringBuffer sb = new StringBuffer();
        sb.append("Place(lon, lat): ");
        sb.append(lon);
        sb.append(", ");
        sb.append(lat);
        searchParamView = findViewById(R.id.search_text);
        searchParamView.setText(sb.toString());

        UpdateListAsynkTask workerThread = new UpdateListAsynkTask(lat, lon, WeatherDatabase.getInstance(getApplicationContext()).daoAccess());
        workerThread.execute();

        initElements();
    }



    //https://stackoverflow.com/questions/7080051/checking-if-difference-between-2-date-is-more-than-20-minutes - time is a bitch
    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean isTimeForUpdate(Date date) {
        boolean result = Minutes.minutesBetween(new DateTime(date), new DateTime())
                .isGreaterThan(Minutes.minutes(20));
        return result;
    }


    private void initElements() {
        recycler_view = (RecyclerView) findViewById(R.id.recycle_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setHasFixedSize(true);
        dateTextView = findViewById(R.id.approved_time);
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

        @Override
        protected List<TimeSeriesInstance> doInBackground(Void... voids) {
            Log.d(TAG, "in async task Make Call - LAT: " + lat + " LON: " + lon);
            HttpHandler httpHandler = new HttpHandler();
            this.response = httpHandler.makeCall(lon, lat);
            if (response != null) {
                Log.d(TAG, "smhirepo - response not null");
            } else {
                Log.d(TAG, "smhirepo - response null");
                return new ArrayList<>();
            }
            return extractDataFromJson();
        }

        private List<TimeSeriesInstance> extractDataFromJson(){
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
                    Log.d(this.getClass().getSimpleName().toUpperCase() + " " + TAG, " dateReformatted: " + stringDate);

                    //convert date value from string to Date format.
                    dateFormat = fromStringToDate(stringDate);
                    //Log.d(this.getClass().getSimpleName().toUpperCase() + " " + TAG, " dateReformatted: " + dateFormat.toString());
                    timeSeriesInstance.setTimeForValues(dateFormat);

                    // Iterate through response set and save relevant info to entities.
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
            String date = String.valueOf(forecastInstance.getSearchTime());
            String[] dateSplit = date.split(" ");
            String[] timeSplit = dateSplit[3].split(":");
            StringBuffer sb = new StringBuffer();
            sb.append(dateSplit[2]);
            sb.append(" ");
            sb.append(dateSplit[1]);
            sb.append(" ");
            sb.append(timeSplit[0]);
            sb.append(":");
            sb.append(timeSplit[1]);
            dateTextView.setText(sb.toString());
            ForecastAdaptor adaptor = new ForecastAdaptor(ts);
            timeSeriesInstancesList = ts;
            adaptor.setTimeSeriesInstanceList(ts);
            recycler_view.setAdapter(adaptor);
        }
    }
}
