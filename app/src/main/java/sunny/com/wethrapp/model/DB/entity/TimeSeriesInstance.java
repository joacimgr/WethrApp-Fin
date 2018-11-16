package sunny.com.wethrapp.model.DB.entity;

import android.arch.persistence.room.ColumnInfo;



public class TimeSeriesInstance {

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
