package com.ashrafmahmood.safelucknow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CasesInUp extends AppCompatActivity {
    TextView total,active, recov, deaths,rNo,oNo,gNo,dRecov,dTotal;
    Spinner rzd,ozd,gzd;
    ImageView refresh;

    DatabaseReference reff;

    ArrayAdapter<String> adapter;
    ArrayList<String> rData;
    ArrayList<String> oData;
    ArrayList<String> gData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cases_in_up);
        total = findViewById(R.id.total);
        active = findViewById(R.id.active);
        recov = findViewById(R.id.recov);
        deaths = findViewById(R.id.deaths);
        dRecov = findViewById(R.id.dRecov);
        dTotal = findViewById(R.id.dTotal);

        rNo = findViewById(R.id.rNo);
        oNo = findViewById(R.id.oNo);
        gNo = findViewById(R.id.gNo);
        refresh = findViewById(R.id.refresh);
        final Animation animation = new RotateAnimation(0.0f, 360.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        animation.setRepeatCount(-1);
        animation.setDuration(2000);

        ((ImageView)findViewById(R.id.refresh)).setAnimation(animation);




        rzd = findViewById(R.id.rzd);
        ozd = findViewById(R.id.ozd);
        gzd = findViewById(R.id.gzd);


        rData =new ArrayList<>();
        adapter = new ArrayAdapter<String>(CasesInUp.this, R.layout.red_spinner, rData);
        rzd.setAdapter(adapter);
        oData =new ArrayList<>();
        adapter = new ArrayAdapter<String>(CasesInUp.this, R.layout.orange_spinner, oData);
        ozd.setAdapter(adapter);
        gData =new ArrayList<>();
        adapter = new ArrayAdapter<String>(CasesInUp.this, R.layout.green_spinner, gData);
        gzd.setAdapter(adapter);


                reff = FirebaseDatabase.getInstance().getReference().child("CasesInUp");







                reff.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {


                        refresh.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ((ImageView)findViewById(R.id.refresh)).clearAnimation();





                        String tot = dataSnapshot.child("Total").getValue().toString();
                        String act = dataSnapshot.child("Active").getValue().toString();
                        String rec = dataSnapshot.child("Recovered").getValue().toString();
                        String dea = dataSnapshot.child("Deaths").getValue().toString();

                        String o = dataSnapshot.child("OrangeZones").getValue().toString();
                        String g = dataSnapshot.child("GreenZones").getValue().toString();
                        String r = dataSnapshot.child("RedZones").getValue().toString();
                                String dR = dataSnapshot.child("DailyRecovered").getValue().toString();
                                String dT = dataSnapshot.child("DailyTotal").getValue().toString();




                        total.setText(tot);
                        active.setText(act);
                        recov.setText(rec);
                        deaths.setText(dea);
                        rNo.setText(r);
                        oNo.setText(o);
                        gNo.setText(g);
                        dRecov.setText(dR);
                        dTotal.setText(dT);




                            }
                        });



                        for(DataSnapshot item:dataSnapshot.child("RedZonesList").getChildren()){

                            rData.add(item.getValue().toString());
                        }
                        adapter.notifyDataSetChanged();
                        for(DataSnapshot item:dataSnapshot.child("OrangeZonesList").getChildren()){

                            oData.add(item.getValue().toString());
                        }
                        adapter.notifyDataSetChanged();
                        for(DataSnapshot item:dataSnapshot.child("GreenZonesList").getChildren()){

                            gData.add(item.getValue().toString());
                        }
                        adapter.notifyDataSetChanged();












                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {

            case R.id.lko:
                Intent intent6 = new Intent(CasesInUp.this,  com.ashrafmahmood.safelucknow.MainActivity.class);
                startActivity(intent6);
                return true;
            case R.id.India:
                Intent intent2 = new Intent(CasesInUp.this,  com.ashrafmahmood.safelucknow.CasesInIndia.class);
                startActivity(intent2);
                return true;

            case R.id.DosDonts:
                Intent intent3 = new Intent(CasesInUp.this,  com.ashrafmahmood.safelucknow.DosDonts.class);
                startActivity(intent3);

                return true;
            case R.id.about:
                Intent intent4 = new Intent(CasesInUp.this,  com.ashrafmahmood.safelucknow.About.class);
                startActivity(intent4);
                return true;


            case R.id.sources:
                Intent intent5 = new Intent(CasesInUp.this,  com.ashrafmahmood.safelucknow.Sources.class);
                startActivity(intent5);
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }
    }
}
