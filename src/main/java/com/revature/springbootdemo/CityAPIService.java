package com.revature.springbootdemo;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CityAPIService {
    public static String getCityInfo(String city) {
        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://api.api-ninjas.com/v1/city?name=" + city)
                    .get()
                    .addHeader("x-api-key", "nv0NPvRl6we5sMjE/HghNQ==VdVyJa2cMfSCRT76")
                    .build();

            Response okHttpResponse = client.newCall(request).execute();
            return okHttpResponse.body().string();
        } catch (Exception e) {
            SpringBootDemoApplication.fileLogger.log(e);
            
            return null;
        }
    }
}
