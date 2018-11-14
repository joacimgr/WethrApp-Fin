package sunny.com.wethrapp.Controller;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import sunny.com.wethrapp.model.DB.entity.Temperature;
import sunny.com.wethrapp.model.WeatherDatabase;

public class ResourceService extends Service {
    WeatherDatabase wdb;

    public ResourceService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        WeatherDatabase wdb = WeatherDatabase.getInstance(getApplicationContext());
        int tempNumber = intent.getIntExtra("tempNumber", 0);
        Temperature temp = new Temperature(tempNumber);
        wdb.daoAccess().insertTemperature(temp);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
