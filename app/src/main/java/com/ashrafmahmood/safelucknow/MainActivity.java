package com.ashrafmahmood.safelucknow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;
    private static final String TAG = "MainActivity";

    TextView total,active, recov, deaths, rz, oz, gz, tvUpdate, tvLink;
    Button btnHotspot;



    DatabaseReference reff;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        total = findViewById(R.id.total);
        active = findViewById(R.id.active);
        recov = findViewById(R.id.recov);
        deaths = findViewById(R.id.deaths);

        btnHotspot = findViewById(R.id.btnHotspot);

        rz = findViewById(R.id.rz);
        oz = findViewById(R.id.oz);
        gz = findViewById(R.id.gz);
        tvUpdate = findViewById(R.id.tvUpdate);
        tvLink = findViewById(R.id.tvLink);

        rz.setVisibility(View.GONE);
        oz.setVisibility(View.GONE);
        gz.setVisibility(View.GONE);
        tvUpdate.setVisibility(View.GONE);
        tvLink.setVisibility(View.GONE);

        btnHotspot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this,  com.ashrafmahmood.safelucknow.Hotspots.class);
                startActivity(intent1);
            }
        });


                reff = FirebaseDatabase.getInstance().getReference().child("LucknowCovid19Cases");



                    reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        /*String tot = dataSnapshot.child("Total").getValue().toString();
                        String act = dataSnapshot.child("Active").getValue().toString();
                        String rec = dataSnapshot.child("Recovered").getValue().toString();
                        String dea = dataSnapshot.child("Deaths").getValue().toString();
                        String sta = dataSnapshot.child("Status").getValue().toString();
                        String up = dataSnapshot.child("UpdatesAvail").getValue().toString();
                        final String upLink = dataSnapshot.child("UpdateLink").getValue().toString();*/
                         String sta = dataSnapshot.child("Status").getValue().toString();
                        if (sta.equalsIgnoreCase("R")) {
                            rz.setVisibility(View.VISIBLE);
                            oz.setVisibility(View.GONE);
                            gz.setVisibility(View.GONE);
                        }
                        else if(sta.equalsIgnoreCase("O")) {
                            oz.setVisibility(View.VISIBLE);
                            rz.setVisibility(View.GONE);
                            gz.setVisibility(View.GONE);
                        }
                        else if(sta.equalsIgnoreCase("G")) {
                            gz.setVisibility(View.VISIBLE);
                            oz.setVisibility(View.GONE);
                            rz.setVisibility(View.GONE);
                        }

                        /*if(up.equalsIgnoreCase("y"))
                        {
                            tvUpdate.setVisibility(View.VISIBLE);
                            tvLink.setVisibility(View.VISIBLE);
                            tvLink.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+upLink));
                                    startActivity(intent);
                                }
                            });
                        }
                        else if (up.equalsIgnoreCase("n"))
                        {
                            tvUpdate.setVisibility(View.GONE);
                            tvLink.setVisibility(View.GONE);
                        }




                        total.setText(tot);
                        active.setText(act);
                        recov.setText(rec);
                        deaths.setText(dea);*/
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.covid19india.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DistrictWiseApi districtWiseApi = retrofit.create(DistrictWiseApi.class);

        Call<districtWise> call = districtWiseApi.getdistrictWise();
        call.enqueue(new Callback<districtWise>() {
            @Override
            public void onResponse(Call<districtWise> call, Response<districtWise> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                districtWise data = response.body();

                        lucknowCases lkoCase = data.getUttar_Pradesh().getDistrictData().getLucknow();

                            active.setText(lkoCase.getActive());
                            total.setText(lkoCase.getConfirmed());
                            recov.setText(lkoCase.getRecovered());
                            deaths.setText(lkoCase.getDeceased());






            }

            @Override
            public void onFailure(Call<districtWise> call, Throwable t) {
                Log.e(TAG, "onFailure: Something went wrong: " + t.getMessage() );
                Toast.makeText(MainActivity.this, "Something went wrong"+t, Toast.LENGTH_SHORT).show();

            }
        });







    }



}
