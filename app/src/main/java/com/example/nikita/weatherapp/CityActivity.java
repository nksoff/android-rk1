package com.example.nikita.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;

import ru.mail.weather.lib.City;
import ru.mail.weather.lib.WeatherStorage;

public class CityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        ((Button) findViewById(R.id.btn_city_VICE_CITY)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeatherStorage.getInstance(CityActivity.this).setCurrentCity(City.VICE_CITY);
                finish();
            }
        });
        ((Button) findViewById(R.id.btn_city_RACCOON_CITY)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeatherStorage.getInstance(CityActivity.this).setCurrentCity(City.RACCOON_CITY);
                finish();
            }
        });
        ((Button) findViewById(R.id.btn_city_SILENT_HILL)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeatherStorage.getInstance(CityActivity.this).setCurrentCity(City.SILENT_HILL);
                finish();
            }
        });
        ((Button) findViewById(R.id.btn_city_SOUTH_PARK)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeatherStorage.getInstance(CityActivity.this).setCurrentCity(City.SOUTH_PARK);
                finish();
            }
        });
        ((Button) findViewById(R.id.btn_city_SPRINGFIELD)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeatherStorage.getInstance(CityActivity.this).setCurrentCity(City.SPRINGFIELD);
                finish();
            }
        });
    }
}
