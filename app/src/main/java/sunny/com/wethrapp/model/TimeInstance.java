package sunny.com.wethrapp.model;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class TimeInstance {

    @PrimaryKey
    public int timeId;

    @ColumnInfo(name = "timeInstance")
    public Date TimeIssue;



}
