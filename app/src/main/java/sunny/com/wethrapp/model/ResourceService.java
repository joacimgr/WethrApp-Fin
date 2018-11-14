package sunny.com.wethrapp.model;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ResourceService extends Service {

    public ResourceService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        System.out.println("\n-----------------------------------\n" + intent.getStringExtra("coordX") + "\n-----------------------------------\n");
        this.stopSelf();
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
