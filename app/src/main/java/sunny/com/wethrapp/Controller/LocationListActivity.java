package sunny.com.wethrapp.Controller;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sunny.com.wethrapp.Controller.parser.HttpHandler;
import sunny.com.wethrapp.Controller.parser.LocationResponse;
import sunny.com.wethrapp.R;
import sunny.com.wethrapp.model.DB.entity.Location;
import sunny.com.wethrapp.model.DaoAccess;
import sunny.com.wethrapp.model.WeatherDatabase;
import sunny.com.wethrapp.view.LocationAdaptor;

public class LocationListActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);
        weatherDatabase = WeatherDatabase.getInstance(getApplicationContext());
        recyclerView = findViewById(R.id.recycle_view_location);
        //Check if bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            searchParameter = (String) bundle.get("searchParam");
        } else {
            searchParameter = "Error";
        }
        Log.d(TAG, "searchParam: " + searchParameter);
        searchParamView = findViewById(R.id.searchParamEditView);
        try{
            UpdateLocationListAsyncTask up = new UpdateLocationListAsyncTask(searchParameter, weatherDatabase.daoAccess());
            up.execute();
        }catch (Exception e){

            System.err.println(e);
        }
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
    public class UpdateLocationListAsyncTask extends AsyncTask<Void, Void, List<Location>> {

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
            //Log.d(TAG, "response: " + locationResponse.get(0).getPlace());
            for (LocationResponse l : locationResponse){
                Log.d(TAG , "Location place: " + l.getPlace());
                Location newLP = new Location();
                newLP.setGeonameid(l.getGeonameid());
                newLP.setLat(l.getLat());
                newLP.setLon(l.getLon());
                newLP.setMunicipality(l.getMunicipality());
                newLP.setPlace(l.getPlace());
                locations.add(newLP);
            }
            Log.d(TAG, " size of locations on async task: " + locations.size());
            daoAccess.insertAllLocations(locations);

            return locations;
        }

        @Override
        protected void onPostExecute(List<Location> locations) {
            LocationAdaptor adaptor = new LocationAdaptor(locations);
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
