package sunny.com.wethrapp.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import sunny.com.wethrapp.model.DB.entity.ForecastInstance;
import sunny.com.wethrapp.model.DB.entity.TimeSeriesInstance;

@Dao
public interface DaoAccess {

    @Insert
    void insertFCInstance(ForecastInstance forcastInstance);

    @Insert
    void insertAllTimeseries(List<TimeSeriesInstance> timeSeriesInstances);

    @Query("SELECT * FROM forecast_table LIMIT 1")
    ForecastInstance getAllForcasts();

    @Query("SELECT * FROM timeseries_table")
    List<TimeSeriesInstance> getAllTimeseries();
}
