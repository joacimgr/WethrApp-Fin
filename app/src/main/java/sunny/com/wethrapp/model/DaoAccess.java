package sunny.com.wethrapp.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import sunny.com.wethrapp.model.DB.entity.ForcastInstance;

@Dao
public interface DaoAccess {

    @Insert
    void insertFCInstance(ForcastInstance forcastInstanc);



}
