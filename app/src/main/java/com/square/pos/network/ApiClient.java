package com.square.pos.network;

import android.content.Context;

import com.square.pos.R;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Prahalad Kumar Chahar
 */
public class ApiClient {

    public static String SUCCESS = "success";
    private static String BASE_URL = "";
    private static String UAT_URL = "";
    private ApiInterface apiService;

    public ApiClient(Context context) {
        BASE_URL = context.getResources().getString(R.string.base_url);
        UAT_URL = "http://13.127.142.101/sanity/ci/";
    }

    public void setCommunication() {
        //logs request and response information
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //call http calls
        OkHttpClient client = new OkHttpClient.
                Builder().
                connectTimeout(2, TimeUnit.MINUTES).
                readTimeout(2, TimeUnit.MINUTES).
                addInterceptor(interceptor).
                build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                //GSON parsing
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiInterface.class);
    }

    public void setUatCommunication() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.
                Builder().
                connectTimeout(2, TimeUnit.MINUTES).
                readTimeout(2, TimeUnit.MINUTES).
                addInterceptor(interceptor).
                build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UAT_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiInterface.class);
    }

    public ApiInterface getApiService() {
        return apiService;
    }
}