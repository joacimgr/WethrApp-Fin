package sunny.com.wethrapp.Controller;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import sunny.com.wethrapp.model.WeatherDatabase;

public class ResourceService extends Service {
    WeatherDatabase wdb;
    private static final String url = "https://maceo.sth.kth.se/api/category/pmp3g/version/2/geotype/point/lon/14.333/lat/60.383/";

    public ResourceService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }







}
