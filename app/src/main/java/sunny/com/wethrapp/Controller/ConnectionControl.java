package sunny.com.wethrapp.Controller;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

/**
 * This class checks network status and returns an enum indication current type of connection.
 */
public class ConnectionControl {

    public static ConnectionType getConnectionType(ConnectivityManager connectivityManager){
        ConnectionType type = ConnectionType.OFFLINE;
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI){
            return ConnectionType.WIFI;
        } else if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE){
            return ConnectionType.MOBILE;
        }
        return ConnectionType.OFFLINE;
    }
}

enum ConnectionType{
    WIFI, MOBILE, OFFLINE
}
