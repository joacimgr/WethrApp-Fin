package sunny.com.wethrapp.model.DB.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;

import java.util.List;

@Entity(tableName = "forecast_table")
public class ForecastInstance {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "search_time")
    private String searchTime;

    @Embedded
    private List<TimeSeriesInstance> timeSeriesInstances;

    public ForecastInstance() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(String searchTime) {
        this.searchTime = searchTime;
    }

    public List<TimeSeriesInstance> getTimeSeriesInstances() {
        return timeSeriesInstances;
    }

    public void setTimeSeriesInstances(List<TimeSeriesInstance> timeSeriesInstances) {
        this.timeSeriesInstances = timeSeriesInstances;
    }
}
