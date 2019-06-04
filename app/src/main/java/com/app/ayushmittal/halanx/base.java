package com.app.ayushmittal.halanx;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ayushmittal.halanx.adapter.recyclerAdapter;
import com.app.ayushmittal.halanx.helperclass.fetch_homes;
import com.app.ayushmittal.halanx.helperclass.logout_helper;
import com.google.android.gms.common.api.Status;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.compat.AutocompleteFilter;
import com.google.android.libraries.places.compat.Place;
import com.google.android.libraries.places.compat.ui.PlaceAutocompleteFragment;
import com.google.android.libraries.places.compat.ui.PlaceSelectionListener;

import java.util.ArrayList;
import java.util.Map;

public class base extends AppCompatActivity  {

    public SharedPreferences sharedPreferences;
     ArrayList<Map<String,String>> reult_list;
    private RecyclerView recyclerView;
    FloatingActionButton fab;

    private LinearLayoutManager layoutManager;
    private LatLng latLng = new LatLng(28.6554,77.1646);
    recyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        reult_list = new ArrayList<Map<String, String>>();

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new recyclerAdapter(reult_list, this);
        recyclerView.setAdapter(recyclerAdapter);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fetch_homes f= new fetch_homes(base.this,reult_list,latLng);
        f.execute();



        sharedPreferences = getSharedPreferences("login_preference", Context.MODE_PRIVATE);



        if(sharedPreferences.getString("key","00").equalsIgnoreCase("00")){

            Intent i= new Intent(this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
        else {
            ((TextView)findViewById(R.id.bottom)).setText("WELCOME  "+sharedPreferences.getString("key","00"));
            Toast.makeText(this,"Showing Results for Delhi",Toast.LENGTH_LONG).show();

        }


        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            @Override
            public void onPlaceSelected(Place place) {
            Toast.makeText(base.this,"Showing Results for  "+place.getAddress().toString(),Toast.LENGTH_LONG).show();
            latLng = place.getLatLng();
                fetch_homes f= new fetch_homes(base.this,reult_list,place.getLatLng());
                f.execute();
            }

            @Override
            public void onError(Status status) {
                Log.e("Error", status.getStatusMessage());
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(base.this);
                builder.setTitle("Filters");
                View view = LayoutInflater.from(base.this).inflate(R.layout.filter_dialog, null);
                builder.setView(view);
                final View temp_view = view;
                builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String par1="",par2="",par3="",par4="";

                        if(((CheckBox)temp_view.findViewById(R.id.full)).isChecked()){
                            par1+="full";
                        }
                        if(((CheckBox)temp_view.findViewById(R.id.semi)).isChecked()){
                            if(!par1.isEmpty())
                                par1+=",";
                                par1+="semi";
                        }
                        if(((CheckBox)temp_view.findViewById(R.id.nil)).isChecked()){
                            if(!par1.isEmpty())
                                par1+=",";
                            par1+="nil";
                        }
                  //      Toast.makeText(base.this,par1,Toast.LENGTH_LONG).show();


                        if(((CheckBox)temp_view.findViewById(R.id.ind)).isChecked()){
                            par2+="independent";
                        }
                        if(((CheckBox)temp_view.findViewById(R.id.apart)).isChecked()){
                            if(!par2.isEmpty())
                                par2+=",";
                            par2+="apartment";
                        }
                        if(((CheckBox)temp_view.findViewById(R.id.villa)).isChecked()){
                            if(!par2.isEmpty())
                                par2+=",";
                            par2+="villa";
                        }

                        if(((CheckBox)temp_view.findViewById(R.id.boys)).isChecked()){
                            par3+="boys";
                        }
                        if(((CheckBox)temp_view.findViewById(R.id.girls)).isChecked()){
                            if(!par3.isEmpty())
                                par3+=",";
                            par3+="girls";
                        }
                        if(((CheckBox)temp_view.findViewById(R.id.family)).isChecked()){
                            if(!par3.isEmpty())
                                par3+=",";
                            par3+="family";
                        }

                        if(((CheckBox)temp_view.findViewById(R.id.flat)).isChecked()){
                            par4+="flat";
                        }
                        if(((CheckBox)temp_view.findViewById(R.id.shared)).isChecked()){
                            if(!par4.isEmpty())
                                par4+=",";
                            par4+="shared";
                        }
                        if(((CheckBox)temp_view.findViewById(R.id.pvt)).isChecked()){
                            if(!par4.isEmpty())
                                par4+=",";
                            par4+="private";
                        }
                        fetch_homes f =new fetch_homes(base.this,reult_list,latLng,par1,par2,par3,par4
                                ,((TextInputEditText)temp_view.findViewById(R.id.bhk)).getText().toString()
                                ,((TextInputEditText)temp_view.findViewById(R.id.rent_min)).getText().toString()
                                    ,((TextInputEditText)temp_view.findViewById(R.id.rent_max)).getText().toString());
                        f.execute();

                    }
                });

                builder.show();
            }

        });



    }


    public void refresh(){
        recyclerView.setAdapter(recyclerAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.logout) {                            //logout option
            logout_helper log = new logout_helper(base.this);
            log.execute();
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar, menu);
        return true;
    }

}
