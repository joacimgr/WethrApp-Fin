package sunny.com.wethrapp.Controller;

import android.annotation.SuppressLint;
import android.os.Build;

public class CheckCoordInRange {
    public static boolean isInRange(double lastLon, double lastLat, double lon, double lat) {
        boolean returnBool;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            double difflat = Double.max(lastLon, lon) - Double.min(lastLon, lon);
            double diffLon = Double.max(lastLat, lat) - Double.min(lastLat, lat);
            if (difflat < 0.08 && diffLon < 0.08) {
                return true;
            }
        }
        return false;
    }
}
