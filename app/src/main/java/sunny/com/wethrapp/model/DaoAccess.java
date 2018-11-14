package sunny.com.wethrapp.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface DaoAccess {
    @Insert
    void insertTimeInstance (Time time);

/*    @Query("SELECT * FROM time WHERE timeId = :timeId")
    Time fetchTimeById(int timeId);*/

    @Query("SELECT * FROM time LIMIT 1")
    Time fetchTimeById();
}
