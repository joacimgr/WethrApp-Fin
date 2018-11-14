package sunny.com.wethrapp.model;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class WeatherCat {

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "wcatno")
    public int weatherCategoryNumber;
}
