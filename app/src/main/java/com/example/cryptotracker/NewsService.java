package com.example.cryptotracker;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class NewsService {

    private static final String BASE_URL = "https://pro-api.coinmarketcap.com/";

    public interface NewsInterface {
        @GET("v1/cryptocurrency/listings/latest?limit=8&CMC_PRO_API_KEY=3062d6d1-ab9c-4a16-8b5d-8518fa6e1279")
        Call<Example> getProperties();
    }

    public static NewsInterface inst;

    static {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        inst = retrofit.create(NewsInterface.class);
    }

}
