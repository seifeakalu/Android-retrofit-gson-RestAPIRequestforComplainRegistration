package com.example.dwhp.remote;

import com.example.dwhp.model.AuthRequest;
import com.example.dwhp.model.User;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {

    @POST("auth/signin")
    Call<AuthRequest> login(@Body AuthRequest body);
    @POST("auth/signup")
    Call<User> signup(@Body User body);
}
