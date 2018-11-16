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
public class ForcastValues {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private int level;
    private String unit;
    private int value;
    private int instanceId;

    public int getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(int instanceId) {
        this.instanceId = instanceId;
    }

    public ForcastValues(String name, int level, String unit, int value) {
        this.name = name;
        this.level = level;
        this.unit = unit;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
