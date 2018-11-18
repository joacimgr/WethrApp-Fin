package sunny.com.wethrapp.model;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.Date;

import sunny.com.wethrapp.model.DB.entity.Converters;
import sunny.com.wethrapp.model.DB.entity.ForecastInstance;
import sunny.com.wethrapp.model.DB.entity.TimeSeriesInstance;

@Database(entities = {ForecastInstance.class, TimeSeriesInstance.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class WeatherDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "WETHERDB.db";
    private static WeatherDatabase dBinstance;

    public abstract DaoAccess daoAccess();

    public static WeatherDatabase getInstance(Context context){
        if(dBinstance == null) {
            dBinstance = Room.databaseBuilder(context.getApplicationContext(), WeatherDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .addCallback(roomCallback)
                    .build();
        }
        return dBinstance;
    }

    public static void destroyInstance(){
        dBinstance = null;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

    private static class InitializeDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {
        private DaoAccess forecastDao;

        private InitializeDatabaseAsyncTask(DaoAccess daoAccess){
            forecastDao = daoAccess;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            forecastDao.insertOneTimeSeries(new TimeSeriesInstance(Converters.fromStringToDate("2018-11-17T14:00Z"), 22.1, 5));
            forecastDao.insertOneTimeSeries(new TimeSeriesInstance(Converters.fromStringToDate("2018-11-17T14:00Z"), 15, 2));
            forecastDao.insertOneTimeSeries(new TimeSeriesInstance(Converters.fromStringToDate("2018-11-17T14:00Z"), 16, 1));
            return null;
        }
    }
}
