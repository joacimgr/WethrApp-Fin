package sunny.com.wethrapp.model.DB.entity;

import android.arch.persistence.room.TypeConverter;

import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Converter to help Room database to store and handle Date and long as timestamp.
 * additional methods to help present date on screen.
 */
public class Converters {

    private static final String TAG = "LogAppTest";

    /**
     * This method receives a string in the format {yyyy-MM-dd'T'hh:mm:ss'Z'} and converts it
     * to a timestamp for easy storage and timediff checks. ie(is date A 20 min before date B).
     * @param input
     * @return
     */
    @TypeConverter
    public static long stringToTimestamp(String input){
        try {
            TimeZone timeZone = TimeZone.getTimeZone("Europe/Stockholm");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            dateFormat.setTimeZone(timeZone);
            Date parsedDate = dateFormat.parse(input);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            //System.out.println(timestamp.getTime());

            //System.out.println(input);
            return timestamp.getTime();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return new Date().getTime();
    }


    /**
     * Converts a timestamp to corresponding Date.
     * @param date
     * @return
     */
    @TypeConverter
    public Date longToDate(long date) {
        return new Date(date);
    }

    public static String dateToStringPresentable(Date date){
        TimeZone tz = TimeZone.getTimeZone("Europe/Stockholm");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM   HH:mm");
        formatter.setTimeZone(tz);
        String s = formatter.format(date);
        return s;
    }

    /**
     * This method compares timestamp searchTime with this instant and returns true if
     * result is more than 20 minutes.
     * @param searchTime
     * @return
     * */
    public static boolean isBeforeTwentyMinutes(long searchTime){
        Date now = new Date();
        if (now.getTime() - searchTime >= 20*60*1000) {
            return true;
        }
        return false;
    }


    /**
     * This method compares timestamp searchTime with this instant and returns true if
     * result is more than 60 minutes.
     * @param searchTime
     * @return
     */
    public static boolean isBeforeOneHour(long searchTime){
        Date now = new Date();
        if (now.getTime() - searchTime >= 60*60*1000) {
            return true;
        }
        return false;
    }

}
