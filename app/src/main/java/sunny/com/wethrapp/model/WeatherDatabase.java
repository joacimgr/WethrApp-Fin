package sunny.com.wethrapp.model;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import sunny.com.wethrapp.model.DB.entity.ForcastInstance;
import sunny.com.wethrapp.model.DB.entity.ForcastValues;

@Database(entities = {ForcastInstance.class, ForcastValues.class}, version = 2, exportSchema = false)
public abstract class WeatherDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "WETHERDB.db";
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
