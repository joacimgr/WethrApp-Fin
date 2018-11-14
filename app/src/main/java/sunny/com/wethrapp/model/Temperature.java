package sunny.com.wethrapp.model;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class Temperature {

    @PrimaryKey
    public int tempId;

    @ColumnInfo(name = "tempC")
    public  int tempC;

}
