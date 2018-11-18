package sunny.com.wethrapp.model.DB.entity;

import android.arch.persistence.room.TypeConverter;

import android.os.Build;
import android.util.Log;

import java.time.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class Converters {

    private static final String TAG = "LogAppTest";

    @TypeConverter
    public static Date fromStringToDate(String stringDate) {
        Date date = null;
        stringDate = stringDate.replace("T", "-");
        stringDate = stringDate.replace("Z", "-");


        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD-HH:mm:ss", Locale.GERMAN);
        try {
            date = sdf.parse(stringDate);
        } catch (ParseException e) {
            Log.d(TAG, "Conversion error in ConvertStringToDate");
            e.printStackTrace();
        }
        return date == null ? new Date() : date;
    }

    @TypeConverter
    public String dateToString(Date date) {
        if (date != null) {
            int YYYY = date.getYear();
            int MM = date.getMonth();
            int DD = date.getDay();
            int HH = date.getHours();
            int mm = date.getMinutes();
            StringBuffer sb = new StringBuffer();
            sb.append(YYYY);
            sb.append("-");
            sb.append(MM);
            sb.append("-");
            sb.append(DD);
            sb.append("T");
            sb.append(HH);
            sb.append(":");
            sb.append(mm);
            sb.append("Z");
            return sb.toString();
        }
        return "error";
    }

}
