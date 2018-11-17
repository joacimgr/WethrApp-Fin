package sunny.com.wethrapp.model.DB.entity;

import android.arch.persistence.room.TypeConverter;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class Converters {

    private static final String TAG = "TypeConverter";

    @TypeConverter
    public static Date fromStringToDate(String stringDate) {
        if (stringDate != null) {
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.z", Locale.ENGLISH);

            try {
                Date date =formatter.parse(stringDate);
                org.joda.time.DateTime d = new DateTime(date.getTime());
                return d.toDate();
            } catch (ParseException e) {
                Log.d(TAG, "stringConverterException");
            }
        }
        return new Date();
    }

    @TypeConverter
    public String dateToString(Date date){
        if(date != null){
            return date.toString();
        }
        return "";
    }

}
