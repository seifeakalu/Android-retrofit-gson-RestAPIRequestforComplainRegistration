package com.example.dwhp.remote;

import com.example.dwhp.model.MessageRequest;
import com.example.dwhp.model.MessageRegister;
import com.example.dwhp.security.TokenUtil;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface MessageService {
    TokenUtil tUtil = new TokenUtil();
    @GET("message")
    // as we are calling data from array so we are calling
    // it with array list and naming that method as getAllCourses();
    Call<MessageRequest> getAllMessages(
            @Header("x-access-token") String token, @Query("page")  int page, @Query("size")  int size
    );

    @POST("message")
    Call<MessageRegister> addMessage(
            @Header("x-access-token") String token,  @Body MessageRegister body
    );

    @PUT("countAgreed")
    Call<Void> countAgreed(@Header("x-access-token") String token,@Query("messageId") int messageId);
    @PUT("countDisagreed")
    Call<Void> countDisagreed(@Header("x-access-token") String token,@Query("messageId") int messageId);

}

