package sunny.com.wethrapp.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import sunny.com.wethrapp.R;
import sunny.com.wethrapp.model.DB.entity.TimeSeriesInstance;
import sunny.com.wethrapp.model.WeatherDatabase;
import sunny.com.wethrapp.Controller.parser.HttpHandler;
import sunny.com.wethrapp.Controller.parser.Response;

public class ShowForecastListActivity extends AppCompatActivity {

    private TextView coordTextView, serviceTextView;
    private Button serviceButton, updateButton;
    private ArrayList<TimeSeriesInstance> responseText;
    private Response response;
    private ForecastRepository forecastRepository;
    private SmhiRepository smhiRepository;
    private static String value = null;
    private static String value2 = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_forecast_list);
        initElements();

        String message = "- : -";

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            value = bundle.getString("inputY");
            value2 = bundle.getString("inputX");
            message = value + " - " + value2;
        }
        coordTextView.setText(message);

        serviceButton.setOnClickListener(view -> new Thread(() -> {
            smhiRepository.makeCall(value,value2);
            response = smhiRepository.getResponse();
            forecastRepository = new ForecastRepository(getApplication(), response);
            forecastRepository.updateForcasts();
            //TODO insert response into database
        }).start());


    }

    private void initElements(){
        coordTextView = findViewById(R.id.coordTextView);
        serviceButton = findViewById(R.id.startServiceButtonView);
        serviceTextView = findViewById(R.id.serviceTextView);
        updateButton = findViewById(R.id.setTextForcastView);
        smhiRepository = new SmhiRepository(getApplication());

    }


}
