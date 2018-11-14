package sunny.com.wethrapp.model;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Time.class}, version = 1, exportSchema = false)
public abstract class WeatherDatabase extends RoomDatabase {
    public abstract DaoAccess daoAccess();
}
