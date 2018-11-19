package sunny.com.wethrapp.Controller;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;

public class CheckCoordInRange {
    public static boolean isInRange(double lastLon, double lastLat, double lon, double lat) {
        boolean returnBool;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            double diffLon = Double.max(lastLon, lon) - Double.min(lastLon, lon);
            double diffLat = Double.max(lastLat, lat) - Double.min(lastLat, lat);
            Log.d("LogAppTest",
                    "lon: " + lon
                            + "\nlat: " + lat
                            + "\nlastLon: " + lastLon
                            + "\nlastLat: " + lastLat
                            + "\ndiffLon: " + diffLon
                            + "\ndiffLat: " + diffLat);
            if (diffLat < 0.08 && diffLon < 0.08) {
                return true;
            }
        }
        return false;
    }
}
