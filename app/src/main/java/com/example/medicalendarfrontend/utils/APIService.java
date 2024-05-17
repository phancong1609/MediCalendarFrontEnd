package com.example.medicalendarfrontend.utils;

import com.example.medicalendarfrontend.requests.LoginRequest;
import com.example.medicalendarfrontend.requests.RegisterRequest;
import com.example.medicalendarfrontend.response.MessageResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIService {
    @POST("/patient/register")
    Call<MessageResponse> register(@Body RegisterRequest request);

    @POST("/patient/login")
    Call<MessageResponse> login(@Body LoginRequest request);

}
