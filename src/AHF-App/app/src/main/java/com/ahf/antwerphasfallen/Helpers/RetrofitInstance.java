package com.ahf.antwerphasfallen.Helpers;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofit;

    //private static final String BASE_URL = "http://antwerphasfallen.azurewebsites.net/api/";
    private static final String BASE_URL = "http://www.antwerphasfallen.appspot.com/api/";


    public static Retrofit getRetrofitInstance(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
        }
        return retrofit;
    }
}