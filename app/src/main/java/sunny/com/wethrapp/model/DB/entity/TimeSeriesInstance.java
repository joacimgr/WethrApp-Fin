package sunny.com.wethrapp.model.DB.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;


/**
 * This is a DB entity representing a weather forecast in a specific instant of time.
 * contains temperature cloud-coverage and time.
 */
@Entity(tableName = "timeseries_table")
public class TimeSeriesInstance {

    @ColumnInfo(name = "forecast_id")
    @PrimaryKey(autoGenerate = true)
    private int instance_id;

    @ColumnInfo(name = "time")
    private long timeForValues;

    @ColumnInfo(name = "temperature")
    private double temperature;

    @ColumnInfo(name = "cloud_coverage")
    private double cloudCoverage;



    @ColumnInfo(name = "symb")
    private int  wsymb;

    public int getInstance_id() {
        return instance_id;
    }

    public void setInstance_id(int instance_id) {
        this.instance_id = instance_id;
    }

    public long getTimeForValues() {
        return timeForValues;
    }

    public void setTimeForValues(long timeForValues) {
        this.timeForValues = timeForValues;
    }

    public double getTemperature() {
        return temperature;
    }

    public TimeSeriesInstance() {
    }

    @Ignore
    public TimeSeriesInstance(long timeForValues, double temperature, double cloudCoverage, int wsymb) {
        this.timeForValues = timeForValues;
        this.temperature = temperature;
        this.cloudCoverage = cloudCoverage;
        this.wsymb = wsymb;
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

    public int getWsymb() {
        return wsymb;
    }

    public void setWsymb(int wsymb) {
        this.wsymb = wsymb;
    }
}
