package com.example.dwhp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dwhp.remote.ApiUtils;
import com.example.dwhp.remote.UserService;
import com.example.dwhp.model.AuthRequest;
import com.example.dwhp.security.TokenUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView goToSinUp;
    EditText edtUsername;
    EditText edtPassword;
    Button btnLogin;
    UserService userService;
    ProgressBar progressBar;
    TokenUtil tokenUtil=new TokenUtil();
   SharedPreferences sh ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        goToSinUp = findViewById(R.id.signUpText);
        edtUsername = findViewById(R.id.editTextEmail);
        edtPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.loginButton);
        userService = ApiUtils.getUserService();
        sh= getSharedPreferences("MySharedPref",MODE_PRIVATE);
        progressBar= findViewById(R.id.idPBLoading);
        progressBar.setVisibility(View.INVISIBLE);
        goToSinUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                //validate form
                if(validateLogin(username, password)){

                    signIn(username,password);
                }
            }
        });
    }
    private boolean validateLogin(String username, String password){
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


    private void signIn(String username,String password){
        progressBar.setVisibility(View.VISIBLE);
        try {
            Call call = userService.login(new AuthRequest(username, password));

            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if(response.isSuccessful()){
                        AuthRequest auth = (AuthRequest) response.body();
                        if(auth.getSuccess()){
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this, auth.getResponseMessage(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this,MainMessageList.class);
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this, "goto sign in", Toast.LENGTH_SHORT).show();
                           tokenUtil.saveToken(auth.getToken(),sh);

                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this, auth.getResponseMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(LoginActivity.this, "connection to the server failed!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }

    }



}
