package sunny.com.wethrapp.model;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class Location {

    @PrimaryKey
    public int lid;

    @ColumnInfo(name = "locationName")
    public String name;

    @ColumnInfo(name = "coordX")
    public double coordX;

    @ColumnInfo(name = "coordY")
    public double coordY;


}
