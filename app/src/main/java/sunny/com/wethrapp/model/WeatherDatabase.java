package sunny.com.wethrapp.model;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import sunny.com.wethrapp.model.DB.entity.CloudCoverage;
import sunny.com.wethrapp.model.DB.entity.Temperature;
import sunny.com.wethrapp.model.DB.entity.Time;
import sunny.com.wethrapp.model.DB.entity.WeatherCategory;

@Database(entities = {Time.class, Temperature.class, WeatherCategory.class, CloudCoverage.class}, version = 1, exportSchema = false)
public abstract class WeatherDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "WETHERDB";
    private static WeatherDatabase dBinstance;

    public abstract DaoAccess daoAccess();

    public static WeatherDatabase getInstance(Context context){
        if(dBinstance == null) {
            dBinstance = Room.databaseBuilder(context.getApplicationContext(), WeatherDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return dBinstance;
    }

    public static void destroyInstance(){
        dBinstance = null;
    }
}
