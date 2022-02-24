package com.revature.springbootdemo;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.InputStream;

/**
 * User Api where each city get the Weather information respectively.
 */

public class WeatherAPIService {

    public static InputStream getCityWeather(String city) {

        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://api.api-ninjas.com/v1/weather?city=" + city)
                    .get()
                    .addHeader("x-api-key", SpringBootDemoApplication.ReadKeys().get(0))
                    .build();

            Response okHttpResponse = client.newCall(request).execute();
            return okHttpResponse.body().byteStream();

        } catch (Exception e) {
            SpringBootDemoApplication.fileLogger.log(e);

            return null;
        }
    }
}
