package sunny.com.wethrapp.model.DB.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(
        foreignKeys = {@ForeignKey(entity = ForcastInstance.class,
                parentColumns = "id",
                childColumns = "instanceId")},
        indices = {@Index(value = {"instanceId"})}
)
public class Temperature {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private double temperature;
    private int instanceId;

    public void setId(int id) {
        this.id = id;
    }

    public double getTemperature() {

        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(int instanceId) {
        this.instanceId = instanceId;
    }

    public Temperature(double temperature, int instanceId) {

        this.temperature = temperature;
        this.instanceId = instanceId;
    }
}
