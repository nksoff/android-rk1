package com.example.nikita.weatherapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ru.mail.weather.lib.City;
import ru.mail.weather.lib.Weather;
import ru.mail.weather.lib.WeatherStorage;
import ru.mail.weather.lib.WeatherUtils;

public class MainActivity extends AppCompatActivity {

    private Intent ourIntent = null;
    private boolean registered = false;
    private BroadcastReceiver receiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ourIntent = new Intent(MainActivity.this, WeatherIntentService.class);
        ourIntent.setAction(WeatherIntentService.ACTION_LOAD);

        findViewById(R.id.btn_city).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CityActivity.class));
            }
        });

        findViewById(R.id.btn_update_background).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!registered) {
                    WeatherUtils.getInstance().schedule(MainActivity.this, ourIntent);
                    startService(ourIntent);
                    registered = true;
                }
            }
        });

        findViewById(R.id.btn_no_update_background).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeatherUtils.getInstance().unschedule(MainActivity.this, ourIntent);
                registered = false;
            }
        });

        final IntentFilter filter = new IntentFilter();
        filter.addAction(WeatherIntentService.ACTION_WEATHER_LOADED);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                MainActivity.this.onUpdate();
            }
        };

        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        if(registered) {
            LocalBroadcastManager.getInstance(MainActivity.this).unregisterReceiver(receiver);
            receiver = null;
            registered = false;
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateCity();
    }

    protected void updateCity() {
        final Button cityButton = (Button) findViewById(R.id.btn_city);
        cityButton.setText(WeatherStorage.getInstance(MainActivity.this).getCurrentCity().toString());

        startService(ourIntent);

        onUpdate();
    }

    protected void onUpdate() {
        Weather weather = WeatherStorage.getInstance(MainActivity.this).getLastSavedWeather(
                WeatherStorage.getInstance(MainActivity.this).getCurrentCity()
        );

        if(weather != null)
            Log.d("weather", weather.getDescription());

        ((TextView) findViewById(R.id.text_weather)).setText(
                (weather == null) ? "..." : Integer.toString(weather.getTemperature()) + " " + weather.getDescription()
        );
    }
}
