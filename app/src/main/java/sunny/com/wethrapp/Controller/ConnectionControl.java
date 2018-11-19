package sunny.com.wethrapp.Controller;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

/**
 * This class checks network status and returns an enum indication current type of connection.
 */
public class ConnectionControl {

    public static ConnectionType getConnectionType(ConnectivityManager connectivityManager){
        ConnectionType type = ConnectionType.OFFLINE;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for(Network network : connectivityManager.getAllNetworks()){
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
                if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
                    type = ConnectionType.WIFI;
                } else if(networkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
                    type = ConnectionType.MOBILE;
                }
            }
            return type;
        }
        return ConnectionType.OFFLINE;
    }
}

enum ConnectionType{
    WIFI, MOBILE, OFFLINE
}
