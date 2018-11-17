package sunny.com.wethrapp.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sunny.com.wethrapp.R;
import sunny.com.wethrapp.model.DB.entity.TimeSeriesInstance;
import sunny.com.wethrapp.model.WeatherDatabase;
import sunny.com.wethrapp.model.parser.HttpHandler;
import sunny.com.wethrapp.model.parser.Response;

public class ShowForecastListActivity extends AppCompatActivity {

    TextView coordTextView, serviceTextView;
    Button serviceButton, updateButton;
    ArrayList<TimeSeriesInstance> responseText;
    Response response;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_forecast_list);
        initElements();
        String message = "- : -";
        String value = null;
        String value2 = null;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            value = bundle.getString("inputX");
            value2 = bundle.getString("inputY");
            message = value + " - " + value2;
        }
        coordTextView.setText(message);

        serviceButton.setOnClickListener(view -> new Thread(() -> {
            HttpHandler httpHandler = new HttpHandler();
            response = httpHandler.makeCall(WeatherDatabase.getInstance(getApplicationContext()));
            startService(new Intent(getApplicationContext(), ResourceService.class));
        }).start());

        updateButton.setOnClickListener(view -> {
            new Thread(() -> responseText = (ArrayList<TimeSeriesInstance>) WeatherDatabase.getInstance(getApplicationContext()).daoAccess().getAllTimeseries());
            serviceTextView.setText(responseText.get(0).getTimeForValues());
        });

    }

    private void initElements(){
        coordTextView = findViewById(R.id.coordTextView);
        serviceButton = findViewById(R.id.startServiceButtonView);
        serviceTextView = findViewById(R.id.serviceTextView);
        updateButton = findViewById(R.id.setTextForcastView);
    }
}
