package com.example.dwhp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dwhp.model.MessageRegister;
import com.example.dwhp.remote.ApiUtils;
import com.example.dwhp.remote.MessageService;
import com.example.dwhp.security.TokenUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterMessage extends AppCompatActivity {
    SharedPreferences sh;
    EditText subject, message;
    Button saveMessage;
    ProgressBar progressBar;
    MessageService messageService;
    TokenUtil tokenUtil = new TokenUtil();
    @SuppressLint("WrongConstant")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_message);
        subject = findViewById(R.id.subject);
        message = findViewById(R.id.message);
        saveMessage = findViewById(R.id.registerButton);
        progressBar= findViewById(R.id.idRegPBLoading);
        progressBar.setVisibility(View.INVISIBLE);
        messageService = ApiUtils.getMessageService();
        sh= getSharedPreferences("MySharedPref", MODE_APPEND);
        saveMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subjectVal = subject.getText().toString();
                String messageVal = message.getText().toString();

                //validate form
                if(validateMessage(subjectVal, messageVal)){
                    saveMessageToDb(subjectVal,messageVal);
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegisterMessage.this,MainMessageList.class);
        startActivity(intent);
        System.out.println(  "onBackPressed Called");

    }
    private boolean validateMessage(String subject, String message){
        if(subject == null || subject.trim().length() == 0){
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(message == null || message.trim().length() == 0){
            Toast.makeText(this, "Firstname is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }



    private void saveMessageToDb(String subject, String message){
        progressBar.setVisibility(View.VISIBLE);
        try {
            Call call = messageService.addMessage(tokenUtil.getToken(sh), new MessageRegister(subject, message,false,""));

            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if(response.isSuccessful()){
                        progressBar.setVisibility(View.INVISIBLE);
                        MessageRegister msgResponse = (MessageRegister) response.body();
                        if(msgResponse.getSuccess()){
                            Toast.makeText(RegisterMessage.this, msgResponse.getResponseMessage(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterMessage.this,MainMessageList.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisterMessage.this, msgResponse.getResponseMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(RegisterMessage.this, "connection to the server failed!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(RegisterMessage.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }

    }

}
