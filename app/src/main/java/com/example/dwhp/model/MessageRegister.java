package com.example.dwhp.model;

public class MessageRegister {
   private String subject;
   private  String message;
   private  String token;
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    private Boolean success=false;

    public MessageRegister(String subject, String message, Boolean success, String responseMessage) {
        this.subject = subject;
        this.message = message;
        this.success = success;
        this.responseMessage = responseMessage;
    }

    private String responseMessage="";

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
