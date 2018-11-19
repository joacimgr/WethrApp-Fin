package sunny.com.wethrapp.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import sunny.com.wethrapp.model.DB.entity.ForecastInstance;
import sunny.com.wethrapp.model.DB.entity.Location;
import sunny.com.wethrapp.model.DB.entity.TimeSeriesInstance;

/**
 * This class Contains the methods needed to access the application database.
 *
 */
@Dao
public interface DaoAccess {

    @Insert
    void insertFCInstance(ForecastInstance forecastInstance);

    @Insert
    void insertAllTimeSeries(List<TimeSeriesInstance> timeSeriesInstances);

    @Insert
    void insertOneTimeSeries(TimeSeriesInstance instance);

    @Insert
    void insertAllLocations(List<Location> locations);

    @Query("SELECT count(*) FROM forecast_table")
    int containsRowsForecast();

    @Query("SELECT * FROM forecast_table LIMIT 1")
    ForecastInstance getForecast();

    @Query("SELECT * FROM timeseries_table")
    List<TimeSeriesInstance> getAllTimeSeries();

    @Query("SELECT * FROM location_table")
    List<Location> getAllLocations();

    @Query("DELETE FROM timeseries_table")
    void deleteAllFromTimeseries();

    @Query("DELETE FROM location_table")
    void deAllFromLocations();

    @Query("DELETE FROM forecast_table")
    void deleteAllFromForecastTable();


}
