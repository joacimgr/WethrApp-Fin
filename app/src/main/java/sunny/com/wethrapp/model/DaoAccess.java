package sunny.com.wethrapp.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import sunny.com.wethrapp.model.DB.entity.CloudCoverage;
import sunny.com.wethrapp.model.DB.entity.Temperature;
import sunny.com.wethrapp.model.DB.entity.Time;
import sunny.com.wethrapp.model.DB.entity.WeatherCategory;

@Dao
public interface DaoAccess {
    @Insert
    void insertTimeInstance (Time time);

    @Insert
    void insertCloudCoverage(CloudCoverage cloudCoverage);

    @Insert
    void insertTemperature (Temperature temperature);

    @Query("SELECT * FROM temperature Where tempId = :tempId LIMIT 1")
    Temperature fetchAllTemp(int tempId);

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
