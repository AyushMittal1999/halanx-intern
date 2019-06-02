package com.app.ayushmittal.halanx.helperclass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
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

public class logout_helper extends AsyncTask<Void,Void,Void> {


    base mact;
    boolean status;

    public logout_helper(base cont) {
        mact = cont;
        status= true;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        if(status){

            mact.sharedPreferences.edit().clear().commit();
            Intent i= new Intent(mact, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mact.startActivity(i);
        }
        else{
            Toast.makeText(mact,"Unable to logout ...", Toast.LENGTH_LONG).show();
        }
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        // Defined URL  where to send data
        URL url = null;
        try {
            url = new URL("http://testapi.halanx.com/rest-auth/logout/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // Send POST data request

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        OutputStreamWriter wr = null;
        try {
            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write("");
            wr.flush();
            wr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            Log.d("code",String.valueOf(conn.getResponseCode()));
            Log.d("msg",conn.getResponseMessage());

            if(conn.getResponseCode()!=HttpURLConnection.HTTP_OK){
                status=false;
            }
            else {
                InputStream inputStream = new BufferedInputStream(conn.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line = "", sb = "";


                while ((line = bufferedReader.readLine()) != null) {
                    sb += line;

                }
                if (sb != null)
                    Log.d("msg1", sb);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        conn.disconnect();

        return null;
    }

}
