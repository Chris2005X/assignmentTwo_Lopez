package com.example.moviesearcher.api;

import com.example.moviesearcher.BuildConfig;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String API_KEY = BuildConfig.OMDB_API_KEY;
    private static final String BASE_URL = BuildConfig.OMDB_BASE_URL;
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static String getApiKey() {
        return API_KEY;
    }
}
