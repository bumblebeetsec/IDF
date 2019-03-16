package com.bumblee.idfscholarship;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetorfitClient {

        private static final String BASE_URL = "https://bumblebeedj.herokuapp.com/";
    private static RetorfitClient mInstance;
    private Retrofit retrofit;

    private RetorfitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public static synchronized RetorfitClient getInstance(){
        if (mInstance == null){
            mInstance = new RetorfitClient();
        }

        return mInstance;
    }

    public Api getApi(){
        return retrofit.create(Api.class);
    }
}
