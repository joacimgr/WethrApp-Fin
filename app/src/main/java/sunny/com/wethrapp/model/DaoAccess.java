package sunny.com.wethrapp.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import sunny.com.wethrapp.model.DB.entity.ForecastInstance;
import sunny.com.wethrapp.model.DB.entity.TimeSeriesInstance;

@Dao
public interface DaoAccess {

    @Insert
    void insertFCInstance(ForecastInstance forecastInstance);

    @Insert
    void insertAllTimeSeries(List<TimeSeriesInstance> timeSeriesInstances);

    @Insert
    void insertOneTimeSeries(TimeSeriesInstance instance);

    @Query("SELECT * FROM forecast_table LIMIT 1")
    ForecastInstance getForcast();

    @Query("SELECT * FROM timeseries_table")
    LiveData<List<TimeSeriesInstance>> getAllTimeSeries();

    @Query("DELETE FROM timeseries_table")
    void deleteAllFromTimeseries();

}
