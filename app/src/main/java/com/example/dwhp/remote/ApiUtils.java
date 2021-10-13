package com.example.dwhp.remote;


public class ApiUtils {

    public static final String BASE_URL = "http://ourattendance.com/newapi/";

    public static UserService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }
    public static MessageService getMessageService(){
        return RetrofitClient.getClientMessage(BASE_URL).create(MessageService.class);
    }

}