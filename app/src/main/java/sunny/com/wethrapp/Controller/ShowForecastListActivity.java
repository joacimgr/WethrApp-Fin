package sunny.com.wethrapp.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import sunny.com.wethrapp.R;
import sunny.com.wethrapp.model.WeatherDatabase;
import sunny.com.wethrapp.model.parser.HttpHandler;

public class ShowForecastListActivity extends AppCompatActivity {

    TextView coordTextView, serviceTextView;
    Button serviceButton, updateButton;
    String responseText;

    private int tempNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_forecast_list);
        initElements();
        responseText = "";
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
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        HttpHandler httpHandler = new HttpHandler();
                        responseText = httpHandler.makeCall(WeatherDatabase.getInstance(getApplicationContext())).getSearchTime();
                    }
                }).start();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serviceTextView.setText(responseText);
            }
        });

    }

    private void initElements(){
        coordTextView = findViewById(R.id.coordTextView);
        serviceButton = findViewById(R.id.startServiceButtonView);
        serviceTextView = findViewById(R.id.serviceTextView);
        updateButton = findViewById(R.id.setTextForcastView);
    }
}
