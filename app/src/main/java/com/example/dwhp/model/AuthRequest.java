package com.example.dwhp.model;

public class AuthRequest {
    private  int  id;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    private String username="";
    private String password="";
    private String token="";
    private Boolean success=false;

    private String responseMessage;
    public String getUserName() {
        return username;
    }

    public void setUserName() {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword() {
        this.password = password;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
