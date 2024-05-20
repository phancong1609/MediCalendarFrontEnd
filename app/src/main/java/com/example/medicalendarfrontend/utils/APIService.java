package com.example.medicalendarfrontend.utils;

import com.example.medicalendarfrontend.requests.CreateProfileRequest;
import com.example.medicalendarfrontend.requests.EditProfileRequest;
import com.example.medicalendarfrontend.requests.LoginRequest;
import com.example.medicalendarfrontend.requests.RegisterRequest;
import com.example.medicalendarfrontend.response.MessageResponse;
import com.example.medicalendarfrontend.response.OfficeResponse;
import com.example.medicalendarfrontend.response.ProfileResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {
    @POST("/patient/register")
    Call<MessageResponse> register(@Body RegisterRequest request);

    @POST("/patient/login")
    Call<MessageResponse> login(@Body LoginRequest request);

    @GET("/office/all")
    Call<OfficeResponse> getallOffice();

    @GET("profile/getprofile/{paitient_email}")
    Call<ProfileResponse> getPatientProfile(@Path("paitient_email") String patientEmail);

    @POST("/profile/create")
    Call<MessageResponse> createProfile(@Body CreateProfileRequest request);

    @POST("/profile/edit")
    Call<MessageResponse> editProfile(@Body EditProfileRequest request);

}
