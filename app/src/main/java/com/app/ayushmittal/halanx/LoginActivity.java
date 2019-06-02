package com.app.ayushmittal.halanx;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import  com.app.ayushmittal.halanx.helperclass.asynctask;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class LoginActivity extends Activity {


    TextView textview;
    TextInputEditText username;
    TextInputEditText password;
    Button btn;
    public SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        btn=(Button) findViewById(R.id.btn);
        username=(TextInputEditText)findViewById(R.id.username);
        password=(TextInputEditText) findViewById(R.id.password);
        sharedPreferences=getSharedPreferences("login_preference", Context.MODE_PRIVATE);


        btn.setOnClickListener(new View.OnClickListener() {                 //Button On click Listener
            @Override
            public void onClick(View v) {
            if(validate(username.getText().toString(),password.getText().toString())){
            asynctask a=new asynctask(LoginActivity.this ,getBaseContext(), username.getText().toString(),password.getText().toString());
            a.execute();
            }
            else{
                    Snackbar.make(getCurrentFocus(),"Plaese Choose Valid Values",Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }


    public boolean validate(String user , String pass){

        if(user==null||pass==null)
            return false;
        else
        return true;


        //Some other conditions can also be set
    }


}


