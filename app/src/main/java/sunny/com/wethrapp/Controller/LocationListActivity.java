package sunny.com.wethrapp.Controller;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import sunny.com.wethrapp.Controller.parser.HttpHandler;
import sunny.com.wethrapp.Controller.parser.LocationResponse;
import sunny.com.wethrapp.R;
import sunny.com.wethrapp.model.DB.entity.Location;
import sunny.com.wethrapp.model.DaoAccess;
import sunny.com.wethrapp.model.WeatherDatabase;
import sunny.com.wethrapp.view.LocationAdaptor;
import sunny.com.wethrapp.view.OnItemClickLocationListener;

public class LocationListActivity extends AppCompatActivity  {

    public WeatherDatabase weatherDatabase;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        weatherDatabase.daoAccess().deAllFromLocations();
    }

    private Context context;
    private String searchParameter;
    private TextView searchParamView;
    private static final String TAG = "LogAppTest";
    protected RecyclerView recyclerView;
    protected List<Location> locationList;
    protected OnItemClickLocationListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);
        weatherDatabase = WeatherDatabase.getInstance(getApplicationContext());
        recyclerView = findViewById(R.id.recycle_view_location);



        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            searchParameter = (String) bundle.get("searchParam");
        } else {
            searchParameter = "Error";
        }
        Log.d(TAG, "searchParam: " + searchParameter);
        searchParamView = findViewById(R.id.searchParamEditView);


        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        ConnectionControl.getConnectionType(connMgr);
        UpdateLocationListAsyncTask up = new UpdateLocationListAsyncTask(searchParameter, weatherDatabase.daoAccess());
        up.execute();

        initElements();

    }

    private void initElements() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(false);
    }



    /**
     * ASYNC TASK - updates result-list of locations from smhi
     */
    public class UpdateLocationListAsyncTask extends AsyncTask<Void, String, List<Location>> {

        private DaoAccess daoAccess;
        private String searchParamPlace;
        private List<LocationResponse> locationResponse;
        private List<Location> locations;

        public UpdateLocationListAsyncTask(String searchParamPlace, DaoAccess daoAccess) {
            this.daoAccess = daoAccess;
            this.searchParamPlace = searchParamPlace;
            this.locationResponse = new ArrayList<>();
            this.locations = new ArrayList<>();
        }

        @Override
        protected List<Location> doInBackground(Void... voids) {

            HttpHandler httpHandler = new HttpHandler();
            this.locationResponse = httpHandler.makeCallLocation(searchParamPlace);
            if(locationResponse != null){
                publishProgress("Loading...");
                for (LocationResponse l : locationResponse){
                    Location newLP = new Location();
                    newLP.setGeonameid(l.getGeonameid());
                    newLP.setLat(l.getLat());
                    newLP.setLon(l.getLon());
                    newLP.setMunicipality(l.getMunicipality());
                    newLP.setPlace(l.getPlace());
                    locations.add(newLP);
                }
                daoAccess.insertAllLocations(locations);
                return locations;
            }
            publishProgress("No matching locations\nSorry Sir/M'am!");
            return new ArrayList<>();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Toast.makeText(getApplicationContext(), values[0], Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(List<Location> locations) {
            OnItemClickLocationListener listener = (view, position) -> {
                Intent intent = new Intent(getApplicationContext(), ShowForecastListActivity.class);
                NumberFormat nf = DecimalFormat.getInstance(Locale.GERMAN);
                DecimalFormat df = (DecimalFormat)nf;
                DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.GERMAN);
                symbols.setDecimalSeparator('.');
                symbols.setGroupingSeparator(',');
                df.setDecimalFormatSymbols(symbols);
                df.setMinimumFractionDigits(0);
                df.setMaximumFractionDigits(3);
                df.setRoundingMode(RoundingMode.DOWN);
                String slon = df.format((locations.get(position).getLon()));
                String slat = df.format((locations.get(position).getLat()));
                String place = locations.get(position).getPlace();
                intent.putExtra("lon", slon);
                intent.putExtra("lat", slat);
                intent.putExtra("place", place);
                startActivity(intent);
            };
            LocationAdaptor adaptor = new LocationAdaptor(locations, listener);
            adaptor.setLocationList(locations);
            this.locations = locations;
            recyclerView.setAdapter(adaptor);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

}
