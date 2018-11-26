package sunny.com.wethrapp.Controller;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;

import java.math.BigDecimal;

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

    /**
     * Checks if longitude and latitude conforms to beeing in format
     * bigger than 0 and smaller than 3 integer digits and not zero.
     * All other values returns true.
     * //TODO implement check if coordinates can possibly produce a response from SMHI
     * @param lon
     * @param lat
     * @return
     */
    public static boolean isCorrectSize(String lon, String lat){
        boolean isWithinValueRange = false;
        Double dLon = Double.parseDouble(lon);
        Double dLat = Double.parseDouble(lat);

        BigDecimal bdLon = BigDecimal.valueOf(dLon.doubleValue());
        BigDecimal bdLat = BigDecimal.valueOf(dLat.doubleValue());
        String stringLon = String.valueOf(bdLon.intValue());
        String stringLat = String.valueOf(bdLat.intValue());

        if(stringLon.length() > 0 && stringLon.length() < 3 &&
                Integer.valueOf(bdLon.intValue()) != 0) {
            if(stringLat.length() > 0 && stringLat.length() < 3 && Integer.valueOf(bdLat.intValue()) != 0) {
                isWithinValueRange = true;
            }
        }
        Log.d("LogAppTest", "value of test: " + isWithinValueRange);
        return isWithinValueRange;
    }
}
