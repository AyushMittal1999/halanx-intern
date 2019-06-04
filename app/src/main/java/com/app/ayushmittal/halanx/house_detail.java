package com.app.ayushmittal.halanx;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class house_detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i=getIntent();
        Map<String,String> m = (Map<String, String>) i.getParcelableArrayListExtra("value").get(i.getIntExtra("position",0));

        TextView textView = (TextView)findViewById(R.id.txt);
        (textView).setText(m.get("name"));

        Set<String> ss = m.keySet();
        Iterator<String> it =ss.iterator();

        while(it.hasNext()){
            String key =it.next();
            textView.append("\n"+key+"  :  "+m.get(key)+"\n");
        }

        URL url = null;
        try {
            url = new URL(m.get("cover_pic_url"));
           Picasso.get().load(url.toString()).into((ImageView)findViewById(R.id.img));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = new Intent(house_detail.this, base.class);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
