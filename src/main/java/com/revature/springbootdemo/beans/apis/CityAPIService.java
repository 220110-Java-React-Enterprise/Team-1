package com.revature.springbootdemo.beans.apis;

import com.revature.springbootdemo.SpringBootDemoApplication;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.InputStream;

public class CityAPIService {

    public static InputStream getCityInfo(String city) {

        try {
            OkHttpClient client = new OkHttpClient();
            System.out.println(SpringBootDemoApplication.ReadKeys().get(0));

            Request request = new Request.Builder()
                    .url("https://api.api-ninjas.com/v1/city?name=" + city)
                    .get()
                    .addHeader("x-api-key", SpringBootDemoApplication.ReadKeys().get(0))
                    .build();

            Response okHttpResponse = client.newCall(request).execute();
            //return okHttpResponse.body().string();
            return okHttpResponse.body().byteStream();

        } catch (Exception e) {
            e.printStackTrace();
            SpringBootDemoApplication.fileLogger.log(e);

            return null;
        }
    }
}

