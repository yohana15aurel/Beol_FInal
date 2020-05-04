package android.example.mocar.Menu_Dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;

import android.example.mocar.Bengkel_Motor.ListBengkelMotor;
import android.example.mocar.Cuci_Mobil.ListCuciMobil;
import android.example.mocar.Cuci_Motor.ListCuciMotor;
import android.example.mocar.Konsultasi.Konsul;
import android.example.mocar.Bengkel_Mobil.ListBengkelMobil;
import android.example.mocar.LocalStorage;
import android.example.mocar.Profile;
import android.example.mocar.R;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Menu extends AppCompatActivity {

    private LinearLayout cardMobil;
    private LinearLayout cardMotor;
    private LinearLayout cardCuciMobil, cardCuciMotor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final LocalStorage localStorage = new LocalStorage();

        cardMobil = findViewById(R.id.cardMobil);
        cardMotor = findViewById(R.id.cardMotor);
        cardCuciMobil = findViewById(R.id.cardCuciMobil);
        cardCuciMotor = findViewById(R.id.cardCuciMotor);

        cardMobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, ListBengkelMobil.class);
                startActivity(intent);
            }
        });
        cardMotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, ListBengkelMotor.class);
                startActivity(intent);
            }
        });

        cardCuciMobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, ListCuciMobil.class);
                startActivity(intent);
            }
        });

        cardCuciMotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, ListCuciMotor.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.NavBot);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.konsul:
                        startActivity(new Intent(getApplicationContext(), Konsul.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });



}}




