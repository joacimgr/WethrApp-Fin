package sunny.com.wethrapp.Controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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

import static sunny.com.wethrapp.model.DB.entity.Converters.dateToStringPresentable;
import static sunny.com.wethrapp.model.DB.entity.Converters.isBeforeOneHour;
import static sunny.com.wethrapp.model.DB.entity.Converters.stringToTimestamp;
import static sunny.com.wethrapp.model.DB.entity.Converters.isBeforeTwentyMinutes;

/**
 * This activity Shows any weather information that it gets presented with
 * either from Database or from Öppna Data API.
 * Contains A recyclerView And information TextViews.
 */
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


        UpdateListAsynkTask workerThread = new UpdateListAsynkTask(lat, lon, WeatherDatabase.getInstance(getApplicationContext()).daoAccess(), context);
        workerThread.execute();

        initElements();
    }


    private void initElements() {
        recycler_view = (RecyclerView) findViewById(R.id.recycle_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setHasFixedSize(true);
        dateTextView = findViewById(R.id.approved_time);
    }


    /**
     * This AsyncTask method decouples information-gathering from the activity's main thread.
     * It will determine the fetching method depending on connectionType and current stored data.
     * This task will update the user with information about it's gather method.
     *
     */
    public class UpdateListAsynkTask extends AsyncTask<Void, String, List<TimeSeriesInstance>> {
        private Response response;
        String lat, lon;
        private static final String TAG = "LogAppTest";
        private DaoAccess forecastDao;
        private ForecastInstance forecastInstance;
        private TimeSeriesInstance timeSeriesInstance;
        private Context context;
        private String[] values;

        public UpdateListAsynkTask(String lat, String lon, DaoAccess forecastDao, Context context) {
            this.lat = lat;
            this.lon = lon;
            this.forecastDao = forecastDao;
            this.forecastInstance = new ForecastInstance();
            this.timeSeriesInstance = new TimeSeriesInstance();
            this.context = context;
        }

        @Override
        protected List<TimeSeriesInstance> doInBackground(Void... voids) {
            boolean makeCall = true;
            ConnectionType type;
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            type = ConnectionControl.getConnectionType(connMgr);
            if(type == ConnectionType.OFFLINE){
                makeCall = false;
                if(forecastDao.containsRowsForecast() > 0){
                    publishProgress("NO CONNECTION\nForecasts might be outdated");
                    forecastInstance = forecastDao.getForecast();
                    timeSeriesInstancesList = forecastDao.getAllTimeSeries();
                } else {
                    publishProgress("NO CONNECTION\nDatabase empty");
                }
            } else if(type == ConnectionType.MOBILE){
                if(forecastDao.containsRowsForecast() > 0){
                    if(isBeforeOneHour(forecastDao.getForecast().getTimestamp())){
                        forecastDao.deleteAllFromForecastTable();
                        forecastDao.deleteAllFromTimeseries();
                        publishProgress("Fetching new information from SMHI\n");
                    } else {
                        makeCall = false;
                        publishProgress("Fetching information from DB");
                        forecastInstance = forecastDao.getForecast();
                        timeSeriesInstancesList = forecastDao.getAllTimeSeries();
                    }
                } else {
                    publishProgress("Fetching new information from SMHI");
                }
            } else {
                if(isBeforeTwentyMinutes(forecastDao.getForecast().getTimestamp())){
                    publishProgress("Fetching new information from SMHI");
                } else {
                    makeCall = false;
                    forecastInstance = forecastDao.getForecast();
                    timeSeriesInstancesList = forecastDao.getAllTimeSeries();
                    publishProgress("Fetching information from local storage");
                }
            }
            if(makeCall){
                forecastDao.deleteAllFromForecastTable();
                forecastDao.deleteAllFromTimeseries();
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
            return timeSeriesInstancesList;

        }

        @Override
        protected void onProgressUpdate(String... values) {
            Toast.makeText(context, values[0], Toast.LENGTH_SHORT).show();
        }

        /**
         * This method iterates over all result-instances and creates entities.
         *
         * @return
         */
        private List<TimeSeriesInstance> extractDataFromJson(){
            try {
                String name;
                forecastInstance = new ForecastInstance();
                timeSeriesInstance = new TimeSeriesInstance();
                ArrayList<TimeSeriesInstance> timeSeriesInstances = new ArrayList<>();
                Response.TimeSeries.Parameters parameters;
                String stringDate;

                double longitude = response.getGeometry().getCoordinates().get(0).get(0);
                double latitude = response.getGeometry().getCoordinates().get(0).get(1);
                forecastInstance.setLongitude(longitude);
                forecastInstance.setLatitude(latitude);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    forecastInstance.setTimestamp(new Date().getTime());
                }

                forecastDao.insertFCInstance(forecastInstance);


                for (int i = 0; i < response.getTimeSeries().size(); i++) {
                    Date dateFormat = null;
                    stringDate = response.getTimeSeries().get(i).getValidTime();

                    dateFormat = new Date(stringToTimestamp(stringDate));
                    timeSeriesInstance.setTimeForValues(dateFormat.getTime());
                    for (int j = 0; j < response.getTimeSeries().get(i).getParameters().size(); j++) {
                        parameters = response.getTimeSeries().get(i).getParameters().get(j);
                        name = response.getTimeSeries().get(i).getParameters().get(j).getName();
                        if (name.equals("t")) { // temperature
                            timeSeriesInstance.setTemperature(parameters.getValues().get(0));
                        } else if (name.equals("tcc_mean")) { // cc
                            timeSeriesInstance.setCloudCoverage(parameters.getValues().get(0));
                        }
                    }
                    timeSeriesInstances.add(timeSeriesInstance);
                    timeSeriesInstance = new TimeSeriesInstance();
                }
                forecastDao.insertAllTimeSeries(timeSeriesInstances);
                return timeSeriesInstances;
            } catch (Exception e) {
                Log.d(TAG, "error when inserting");
            }
            Log.d(TAG, "new ArrayList<>() from extractDataFromJson()");
            return new ArrayList<>();
        }

        /**
         * When doInBackground is finished, This method will update the UI
         * and fill The view with information.
         * @param ts
         */
        @Override
        protected void onPostExecute(List<TimeSeriesInstance> ts) {
            Date date = new Date(forecastInstance.getTimestamp());
            String dateText = dateToStringPresentable(date);
            dateTextView.setText(dateText);
            ForecastAdaptor adaptor = new ForecastAdaptor(ts);
            timeSeriesInstancesList = ts;
            adaptor.setTimeSeriesInstanceList(ts);
            recycler_view.setAdapter(adaptor);
        }
    }
}
