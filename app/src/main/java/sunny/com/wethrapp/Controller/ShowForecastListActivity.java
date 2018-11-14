package sunny.com.wethrapp.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import sunny.com.wethrapp.R;
import sunny.com.wethrapp.model.WeatherDatabase;

public class ShowForecastListActivity extends AppCompatActivity {

    TextView coordTextView, serviceTextView;
    Button serviceButton, updateButton;

    private int tempNumber = 1;

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

        serviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResourceService.class);
                tempNumber++;
                intent.putExtra("tempNumber", tempNumber);
                startService(intent);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String temperature = String.valueOf(WeatherDatabase
                        .getInstance(getApplicationContext())
                        .daoAccess()
                        .fetchAllTemp(tempNumber+8)
                        .getTemperature());
                serviceTextView.setText(temperature);
            }
        });
    }

    private void initElements(){
        coordTextView = findViewById(R.id.coordTextView);
        serviceButton = findViewById(R.id.startServiceButtonView);
        updateButton = findViewById(R.id.updateServiceButton);
        serviceTextView = findViewById(R.id.serviceTextView);
    }
}
