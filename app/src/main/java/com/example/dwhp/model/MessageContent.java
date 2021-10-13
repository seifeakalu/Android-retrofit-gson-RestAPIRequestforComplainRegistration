package com.example.dwhp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageContent {
    public MessageContent(String subject, String message) {
        this.subject = subject;
        this.message = message;
    }

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("subject")
    @Expose
    private String subject ;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("agree_count")
    @Expose
    private String agreeCount;
    @SerializedName("disagree_count")
    @Expose
    private String disagreeCount;
    public String getAgreeCount() {
        return agreeCount;
    }

    public void setAgreeCount(String agree_count) {
        this.agreeCount = agree_count;
    }

    public String getDisagreeCount() {
        return disagreeCount;
    }

    public void setDisagreeCount(String disagree_count) {
        this.disagreeCount = disagree_count;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
