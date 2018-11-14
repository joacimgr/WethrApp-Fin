package sunny.com.wethrapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class CloudCoverage {

    @PrimaryKey(autoGenerate = true)
    public int ccId;

    @ColumnInfo(name = "clouds")
    private int cloudCoverMagnitude;

    public CloudCoverage(int cloudCoverMagnitude) {
        this.cloudCoverMagnitude = cloudCoverMagnitude;
    }

    public int getCloudCoverMagnitude() {
        return cloudCoverMagnitude;
    }

    public void setCloudCoverMagnitude(int cloudCoverMagnitude) {
        this.cloudCoverMagnitude = cloudCoverMagnitude;
    }
}
