package sunny.com.wethrapp.model.DB.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ForcastInstance {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private Double[] coorinates;

    private ArrayList<ForcastValues> forcastValues;
    private String approvedTime;

    public ForcastInstance() {
    }

    public ForcastInstance(Double[] coorinates, ArrayList<ForcastValues> forcastValues, String approvedTime) {
        this.coorinates = coorinates;
        this.forcastValues = forcastValues;
        this.approvedTime = approvedTime;
    }

    public List<ForcastValues> getForcastValues() {
        return forcastValues;
    }

    public void setForcastValues(List<ForcastValues> forcastValues) {
        this.forcastValues = (ArrayList<ForcastValues>) forcastValues;
    }

    public String getApprovedTime() {
        return approvedTime;
    }

    public void setApprovedTime(String approvedTime) {
        this.approvedTime = approvedTime;
    }

    public List<ForcastValues> getValues() {
        return forcastValues;
    }

    public void setValues(List<ForcastValues> forcastValues) {
        this.forcastValues = (ArrayList<ForcastValues>) forcastValues;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Double[] getCoorinates() {
        return coorinates;
    }
    public void setCoorinates(Double[] coorinates) {
        this.coorinates = coorinates;
    }
}
