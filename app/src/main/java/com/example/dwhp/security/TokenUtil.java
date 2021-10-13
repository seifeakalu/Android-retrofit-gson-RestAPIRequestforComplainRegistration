package com.example.dwhp.security;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dwhp.LoginActivity;
import com.example.dwhp.MainMessageList;

import javax.crypto.Cipher;

public class TokenUtil  {
    Aes256Class aes256 = new Aes256Class();
    public Boolean saveToken(String token,SharedPreferences sharedPreferences){
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("token", token);
        byte[] encryptedString = aes256.makeAes(token.getBytes(), Cipher.ENCRYPT_MODE);
        Log.d("Encoded string: ", new String(encryptedString));

        if(myEdit.commit()){
            return true;
        }
        else {
            return false;
        }

    }

    public String getToken(SharedPreferences sharedPreferences){

        byte[] decodedString  = sharedPreferences.getString("token", "").getBytes();
        String string = new String(decodedString);
        return string;

    }
}
