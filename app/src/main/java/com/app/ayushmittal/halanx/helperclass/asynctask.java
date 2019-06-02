package com.app.ayushmittal.halanx.helperclass;

// For login POST request


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.TextPaint;
import android.util.Log;
import android.widget.Toast;

import com.app.ayushmittal.halanx.LoginActivity;
import com.app.ayushmittal.halanx.base;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class asynctask extends AsyncTask<Void,Void,Void> {

    String user, pass;
    ProgressDialog dialog;
    boolean status;
    Context mcontext;
    LoginActivity activity;

    public asynctask(LoginActivity act,Context context , String username , String password) {
        user=username;
        pass=password;
        status=true;
        dialog= new ProgressDialog(act);
        activity=act;
        mcontext=context;
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Logging in ....");
        dialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        dialog.dismiss();
        if(status==false&&(mcontext!=null))
         Toast.makeText(mcontext,"Invalid credential ",Toast.LENGTH_LONG).show();
        else{

            // Start Home Activity
            Intent i= new Intent(activity,base.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mcontext.startActivity(i);
        }
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        URL url = null;
        try {
            url = new URL("http://testapi.halanx.com/rest-auth/login/?");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // Send POST data request

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            //conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        OutputStreamWriter wr = null;
        try {
            wr = new OutputStreamWriter(conn.getOutputStream());
            StringBuilder send = new StringBuilder();
            send.append( URLEncoder.encode("password","UTF-8"));
            send.append("=");
            send.append(URLEncoder.encode(pass,"UTF-8"));
            send.append("&");
            send.append( URLEncoder.encode("username","UTF-8"));
            send.append("=");
            send.append( URLEncoder.encode(user,"UTF-8"));

            Log.d("utf",send.toString());
            wr.write(send.toString());
            wr.flush();
            wr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            Log.d("code",String.valueOf(conn.getResponseCode()));
            Log.d("msg",conn.getResponseMessage());

            if(conn.getResponseCode()==HttpURLConnection.HTTP_OK) {

                InputStream inputStream = new BufferedInputStream(conn.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line = "", sb = "";


                while ((line = bufferedReader.readLine()) != null) {
                    sb += line;

                }
                if (sb != null)
                    Log.d("msg1", sb);


                SharedPreferences.Editor editor= activity.sharedPreferences.edit();
                editor.putString("key",sb);
                editor.commit();
            }
            else{
                status=false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        conn.disconnect();

        return null;

    }
}
