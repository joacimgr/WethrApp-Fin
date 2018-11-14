package sunny.com.wethrapp;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


import sunny.com.wethrapp.model.Time;
import sunny.com.wethrapp.model.WeatherDatabase;

public class MainActivity extends AppCompatActivity {

    private static final String DATABASE_NAME = "WETHERDB";
    public WeatherDatabase weatherDatabase;
    public TextView infoViewText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherDatabase = Room.databaseBuilder(getApplicationContext(),
                WeatherDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Time time = new Time(55);
                weatherDatabase.daoAccess().insertTimeInstance(time);
                infoViewText = (TextView) findViewById(R.id.infoViewTime);
                int time2 = weatherDatabase.daoAccess().fetchTimeById().getTime();
                infoViewText.setText(String.valueOf(time2));
            }
        }).start();


    }




}
