package sunny.com.wethrapp.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface DaoAccess {
    @Insert
    void insertTimeInstance (Time time);

    @Insert
    void insertCloudCoverage(CloudCoverage cloudCoverage);

    @Insert
    void insertTemperature (Temperature temperature);

    @Insert
    void insertWeatherCategory (WeatherCategory weatherCategory);

    @Query("SELECT * FROM time LIMIT 1")
    Time fetchTimeById();

    @Query("DELETE FROM temperature")
    void nukeTableTemperature();

    @Query("DELETE FROM time")
    void nukeTableTime();

    @Query("DELETE FROM cloudCoverage")
    void nukeTableCloudCoverage();

    @Query("DELETE FROM weatherCategory")
    void nukeTableWeatherCategory();
}
