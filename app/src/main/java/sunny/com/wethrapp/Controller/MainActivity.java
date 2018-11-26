package sunny.com.wethrapp.Controller;

import android.content.Context;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sunny.com.wethrapp.R;
import sunny.com.wethrapp.model.WeatherDatabase;

import static sunny.com.wethrapp.Controller.CheckCoordInRange.isCorrectSize;

/**
 * This Activity serves as the welcome screen. It contains editTextViews for
 * longitude, latitude and location name search.
 *
 */
public class MainActivity extends AppCompatActivity {

    public WeatherDatabase weatherDatabase;
    private Context context;
    public Button buttonNextView;
    public Button buttonLocationView;
    public EditText longitud, latitude, searchParamView;
    private static final String TAG = "LogAppTest";

    /**
     * This method will execute when the activity is created and recreated when screen-flip occurs.
     * Before continuing to showforecastlistactivity it stores lat, long in the intent.
     * Search edit text field is saved to intent for LocationListActivity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        weatherDatabase = WeatherDatabase.getInstance(context);

        longitud = findViewById(R.id.inputLonText);
        latitude = findViewById(R.id.inputLatText);
        searchParamView = findViewById(R.id.searchParamEditView);

        buttonLocationView = findViewById(R.id.main_button_go_view);
        buttonNextView = findViewById(R.id.buttonNextView);

        buttonNextView.setOnClickListener(view -> {
            if(isCorrectSize(longitud.getText().toString(), latitude.getText().toString())){
                Intent intent = new Intent(getApplicationContext(), ShowForecastListActivity.class);
                intent.putExtra("lon", longitud.getText().toString());
                intent.putExtra("lat", latitude.getText().toString());
                startActivity(intent);
            } else {
                Toast.makeText(context,"Coordinates error\nexample: 60.383, 14.333",
                        Toast.LENGTH_SHORT).show();
            }
        });

        buttonLocationView.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), LocationListActivity.class);
            intent.putExtra("searchParam", searchParamView.getText().toString());
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        if(weatherDatabase != null){
            weatherDatabase = null;
        }
        super.onDestroy();
    }
}
