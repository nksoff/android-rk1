package com.example.nikita.weatherapp;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.IOException;

import ru.mail.weather.lib.City;
import ru.mail.weather.lib.Weather;
import ru.mail.weather.lib.WeatherStorage;
import ru.mail.weather.lib.WeatherUtils;

public class WeatherIntentService extends IntentService {

    public final static String ACTION_LOAD = "ru.mail.park.WEATHER_LOAD_ACTION";
    public final static String ACTION_WEATHER_LOADED = "ru.mail.park.WEATHER_LOADED_ACTION";


    public WeatherIntentService() {
        super("WeatherIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent != null) {
            final String action = intent.getAction();
            Log.d("WeatherIntentService", "intent");

            if(ACTION_LOAD.equals(action)) {
                Log.d("WeatherIntentService", "intent " + ACTION_LOAD);
                final City city = WeatherStorage.getInstance(WeatherIntentService.this).getCurrentCity();
                try {
                    Weather weather = WeatherUtils.getInstance().loadWeather(city);
                    WeatherStorage.getInstance(WeatherIntentService.this).saveWeather(city, weather);
                }
                catch(IOException e) {
                    Log.d("WeatherIntentService", "error loading");
                }

                final Intent outIntent = new Intent(ACTION_WEATHER_LOADED);
                LocalBroadcastManager.getInstance(this).sendBroadcast(outIntent);
            }
        }
    }
}
