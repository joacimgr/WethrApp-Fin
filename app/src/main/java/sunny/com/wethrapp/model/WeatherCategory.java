package sunny.com.wethrapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class WeatherCategory {

    @PrimaryKey(autoGenerate = true)
    public int wcId;

    @ColumnInfo(name = "cat")
    private int category;

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public WeatherCategory(int category) {
        this.category = category;
    }
}
