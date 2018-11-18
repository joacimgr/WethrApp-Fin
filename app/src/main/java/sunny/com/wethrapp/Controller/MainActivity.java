package sunny.com.wethrapp.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;


import sunny.com.wethrapp.R;
import sunny.com.wethrapp.model.WeatherDatabase;

public class MainActivity extends AppCompatActivity {

    public WeatherDatabase weatherDatabase;
    public Button buttonNextView;
    public EditText inputTextX;
    public EditText inputTextY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherDatabase = WeatherDatabase.getInstance(getApplicationContext());

        inputTextX = findViewById(R.id.inputLatText);
        inputTextY = findViewById(R.id.inputLonText);

        buttonNextView = findViewById(R.id.buttonNextView);
        buttonNextView.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ShowForecastListActivity.class);
            intent.putExtra("inputX", inputTextX.getText().toString());
            intent.putExtra("inputY", inputTextY.getText().toString());
            startActivity(intent);
        });
    }


}
