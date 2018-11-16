package sunny.com.wethrapp.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import sunny.com.wethrapp.model.DB.entity.ForcastInstance;
import sunny.com.wethrapp.model.DB.entity.ForcastValues;

@Dao
public interface DaoAccess {

    @Insert
    void insertFCInstance(ForcastInstance forcastInstanc);

    @Query("SELECT * FROM forcastinstance LIMIT 1")
    ForcastInstance getForCastInstanceById();



}
