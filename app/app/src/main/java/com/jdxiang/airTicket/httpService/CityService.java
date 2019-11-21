package com.jdxiang.airTicket.httpService;

import com.jdxiang.airTicket.R;
import com.jdxiang.airTicket.entity.City;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CityService {

    OkHttpClient okHttpClient = new OkHttpClient();

    static final String BASE_URL = "http://127.0.0.1:8080/";


    public List<City> getAll() {
        new Thread(() -> {
            try {
                Request request = new Request.Builder()
                        .url(BASE_URL + "city")
                        .addHeader("Accept", "application/json")
                        .method("GET", null)
                        .build();

                Response response = this.okHttpClient.newCall(request).execute();
                System.out.println(response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return null;
    }

}
