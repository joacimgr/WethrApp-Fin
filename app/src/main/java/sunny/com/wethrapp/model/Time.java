package sunny.com.wethrapp.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Time {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int timeId;

    @ColumnInfo(name = "time")
    private int time;

    public int getTimeId() {
        return timeId;
    }

    public void setTimeId(int timeId) {
        this.timeId = timeId;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Time(int time) {
        this.time = time;
    }
}
