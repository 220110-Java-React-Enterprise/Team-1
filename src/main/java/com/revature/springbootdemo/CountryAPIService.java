package com.revature.springbootdemo;

import com.revature.springbootdemo.SpringBootDemoApplication;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.InputStream;

/**
 * User Api to retrieve each Country for their information.
 */

public class CountryAPIService {

    public static InputStream getCountryInfo(String country) {

        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://api.api-ninjas.com/v1/country?name=" + country)
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
