package com.revature.springbootdemo;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherAPIService {
    public static String getCityWeather(String city) {
        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://api.api-ninjas.com/v1/weather?city=" + city)
                    .get()
                    .addHeader("x-api-key", "")
                    .build();

            Response okHttpResponse = client.newCall(request).execute();
            return okHttpResponse.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            
            return null;
        }
    }
}
