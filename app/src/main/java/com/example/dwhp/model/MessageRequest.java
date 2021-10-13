package com.example.dwhp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MessageRequest {
    @SerializedName("content")
    @Expose
    private ArrayList<MessageContent> messageContent = null;
    @SerializedName("totalPageNos")
    @Expose
    private int totalPageNos;
    @SerializedName("totalElements")
    @Expose
    private int totalElements;

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }





    public int getTotalPageNos() {
        return totalPageNos;
    }

    public void setTotalPageNos(int totalPageNos) {
        this.totalPageNos = totalPageNos;
    }



    public ArrayList<MessageContent> getMessageContent() {
        return messageContent;
    }

    public MessageRequest(ArrayList<MessageContent> messageContent) {
        super();

        this.messageContent = messageContent;
    }

    public void setMessageContent(ArrayList<MessageContent> messageContent) {
        this.messageContent = messageContent;
    }
}
