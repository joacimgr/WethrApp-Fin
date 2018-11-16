package sunny.com.wethrapp.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import sunny.com.wethrapp.model.DB.entity.ForecastInstance;

@Dao
public interface DaoAccess {

    @Insert
    void insertFCInstance(ForecastInstance forcastInstance);

    @Query("SELECT * FROM forecast_table LIMIT 1")
    ForecastInstance getAllForcasts();


}
