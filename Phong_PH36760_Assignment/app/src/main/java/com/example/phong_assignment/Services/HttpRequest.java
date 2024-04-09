package com.example.phong_assignment.Services;

import static com.example.phong_assignment.Services.APIServices.BASE_URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HttpRequest {

    private APIServices apiServices;

    public HttpRequest() {
        apiServices = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(APIServices.class);
    }

    public APIServices callApi(){
        return apiServices;
    }
}
