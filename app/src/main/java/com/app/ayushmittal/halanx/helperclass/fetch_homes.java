package com.app.ayushmittal.halanx.helperclass;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.app.ayushmittal.halanx.base;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class fetch_homes extends AsyncTask<Void,Void,Void> {


    ArrayList<Map<String,String>> al;
    base activity;
    String cons="";
    LatLng latLng;
    public fetch_homes(ArrayList<Map<String,String>> reult_list ) {

        al = reult_list;
        al.clear();
    }
    public fetch_homes(base act, ArrayList<Map<String,String>> reult_list , LatLng mlatLng) {

        al = reult_list;
        al.clear();

        activity=act;
        latLng = mlatLng;
    }


    public fetch_homes(base act, ArrayList<Map<String,String>> reult_list , LatLng mlatLng,String par1
            ,String par2,String par3,String par4,String bhk , String min_cost,String maxcost) {

        al = reult_list;
        al.clear();

        activity=act;
        latLng = mlatLng;

        cons+="&radius=1000";
        cons+="&furnish_type="+par1;
        cons+="&house_type="+par2;
        cons+="&accomodation_allowed="+par3;
        cons+="&accomodation_type="+par4;
        cons+="&bhk_count="+bhk;
        cons+="&rent_min="+min_cost;
        cons+="&rent_max="+maxcost;

    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        activity.refresh();
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {


            String tempurl ="http://testapi.halanx.com/homes/houses/?format=json";

            tempurl+="&latitude=";
            tempurl+=String.valueOf(latLng.latitude);
            tempurl+="&longitude=";
            tempurl+=String.valueOf(latLng.longitude);
            tempurl+=cons;

            URL url=new URL(tempurl);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line ="",json="";

            while (line!=null){

                line=bufferedReader.readLine();
                json=json+line;
            }

            json = (new JSONObject(json)).getString("results");

            JSONArray jsonArray=new JSONArray(json);

            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Map<String,String> tempmap = new HashMap<String ,String>();

                Iterator<String> iterator= jsonObject.keys();
                while (iterator.hasNext()){
                    String key =iterator.next();
                    tempmap.put(key,jsonObject.getString(key));
                }
                al.add(tempmap);

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;


    }
}
