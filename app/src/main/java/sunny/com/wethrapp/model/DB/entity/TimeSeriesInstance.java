package sunny.com.wethrapp.model.DB.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "timeseries_table")
public class TimeSeriesInstance {

    @ColumnInfo(name = "forecast_id")
    @PrimaryKey(autoGenerate = true)
    private int instance_id;

    public int getInstance_id() {
        return instance_id;
    }

    public void setInstance_id(int instance_id) {
        this.instance_id = instance_id;
    }


    @ColumnInfo(name = "time")
    private String timeForValues;

    @ColumnInfo(name = "temperature")
    private double temperature;

    @ColumnInfo(name = "cloud_coverage")
    private double cloudCoverage;


    public String getTimeForValues() {
        return timeForValues;
    }

    public void setTimeForValues(String timeForValues) {
        this.timeForValues = timeForValues;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getCloudCoverage() {
        return cloudCoverage;
    }

    public void setCloudCoverage(double cloudCoverage) {
        this.cloudCoverage = cloudCoverage;
    }


}
