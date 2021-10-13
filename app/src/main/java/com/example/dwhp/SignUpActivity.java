package com.example.dwhp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dwhp.model.User;
import com.example.dwhp.remote.ApiUtils;
import com.example.dwhp.remote.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    EditText email;
    TextView goToSinIn;
    EditText password;
    EditText userName;
    EditText firstName;
    EditText lastName;
    Button btnSignUp;
    UserService userService;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        email = findViewById(R.id.emailText);
        password = findViewById(R.id.passwordText);
        userName = findViewById(R.id.usernameText);
        firstName = findViewById(R.id.firstnameText);
        lastName = findViewById(R.id.lastnameText);
        btnSignUp = findViewById(R.id.signupButton);
        goToSinIn = findViewById(R.id.signInButton);
        progressBar= findViewById(R.id.idPBLoading);
        progressBar.setVisibility(View.INVISIBLE);
        userService = ApiUtils.getUserService();


        goToSinIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
                Toast.makeText(SignUpActivity.this, "goto sign in", Toast.LENGTH_SHORT).show();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailVal = email.getText().toString();
                String passwordVal = password.getText().toString();
                String usernameVal = userName.getText().toString();
                String firstnameVal = firstName.getText().toString();
                String lastnameVal = firstName.getText().toString();
                //validate form
                if(validateLogin(emailVal, passwordVal,usernameVal,firstnameVal,lastnameVal)){
                    signUp(emailVal, passwordVal,usernameVal,firstnameVal,lastnameVal);
                }
            }
        });
    }
    private boolean validateLogin(String email, String password, String username, String firstName, String lastName){
        if(email == null || email.trim().length() == 0){
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(firstName == null || firstName.trim().length() == 0){
            Toast.makeText(this, "Firstname is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(lastName == null || lastName.trim().length() == 0){
            Toast.makeText(this, "Lastname is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(username == null || username.trim().length() == 0){
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password == null || password.trim().length() == 0){
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void signUp(String email, String password, String username, String firstname, String lastname){
        progressBar.setVisibility(View.VISIBLE);
        try {
            Call call = userService.signup(new User(email, password,username,firstname,lastname,false,"initial"));

            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if(response.isSuccessful()){
                        progressBar.setVisibility(View.INVISIBLE);
                        User userResponse = (User) response.body();
                        if(userResponse.getSuccess()){
                            Toast.makeText(SignUpActivity.this, userResponse.getResponseMessage(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignUpActivity.this, userResponse.getResponseMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(SignUpActivity.this, "connection to the server failed!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }

    }



}

