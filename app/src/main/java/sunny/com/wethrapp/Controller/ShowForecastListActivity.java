package sunny.com.wethrapp.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import sunny.com.wethrapp.R;
import sunny.com.wethrapp.model.ResourceService;

public class ShowForecastListActivity extends AppCompatActivity {

    TextView coordTextView;
    Button serviceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_forecast_list);
        coordTextView = findViewById(R.id.coordTextView);

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

        serviceButton = findViewById(R.id.startServiceButtonView);
        serviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResourceService.class);
                intent.putExtra("coordX",  "200");
                startService(intent);
            }
        });
    }
}
