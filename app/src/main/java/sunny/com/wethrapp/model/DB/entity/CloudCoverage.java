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
public class CloudCoverage {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int cloudCoverageLevel;
    private int instanceId;

    public void setId(int id) {
        this.id = id;
    }

    public int getCloudCoverageLevel() {

        return cloudCoverageLevel;
    }

    public void setCloudCoverageLevel(int cloudCoverageLevel) {
        this.cloudCoverageLevel = cloudCoverageLevel;
    }

    public int getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(int instanceId) {
        this.instanceId = instanceId;
    }

    public CloudCoverage(int cloudCoverageLevel, int instanceId) {
        this.cloudCoverageLevel = cloudCoverageLevel;
        this.instanceId = instanceId;
    }
}
