package com.example.bmicalculator;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.Date;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {

    private TextView tvBMIValue, tvBMICategory, tvAdviceText;
    private Button btnRecalculateBMI, btnSaveResult;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

        tvBMIValue= findViewById(R.id.tvBMIValue);
        tvBMICategory= findViewById(R.id.tvBMICategory);
        tvAdviceText= findViewById(R.id.tvAdviceText);
        btnRecalculateBMI= findViewById(R.id.btnRecalculateBMI);
        btnSaveResult= findViewById(R.id.btnSaveResult);

        drawerLayout= findViewById(R.id.main);
        navigationView= findViewById(R.id.navigationView);
        toolbar= findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.OpenDrawer, R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id= item.getItemId();

                if (id==R.id.opt_home){
                    Intent historyIntent = new Intent(ResultActivity.this, MainActivity.class);
                    startActivity(historyIntent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else if (id==R.id.opt_history){
                    Intent historyIntent = new Intent(ResultActivity.this, HistoryActivity.class);
                    startActivity(historyIntent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                return true;
            }
        });

        //Get Data from Intent
        Intent intent= getIntent();
        double bmiValue= intent.getDoubleExtra("BMI_VALUE", 0.0);
        String bmiCategory= intent.getStringExtra("BMI_CATEGORY");
        String advice= intent.getStringExtra("ADVICE");

        //Set Data to Views
        tvBMIValue.setText(String.format("%.1f", bmiValue));
        tvBMICategory.setText(bmiCategory);
        tvAdviceText.setText(advice);

        //Handle Recalculate Button
        btnRecalculateBMI.setOnClickListener(v -> {
            Intent recalculateIntent= new Intent(ResultActivity.this, MainActivity.class);
            startActivity(recalculateIntent);
            finish();
        });

        //Handle Save Result Button
        btnSaveResult.setOnClickListener(v -> {
            saveBMIResult(bmiValue, bmiCategory);
            Toast.makeText(ResultActivity.this, "Result Saved", Toast.LENGTH_LONG).show();
        });

    }

    private void saveBMIResult(double bmiValue, String bmiCategory) {
        String currentDateTime = getCurrentDateTime(); // Method to get current date and time

        // Save the result in the Room Database
        new Thread(() -> {
            BMIDatabase db = BMIDatabase.getInstance(this);
            BMIResult bmiResult = new BMIResult(bmiValue, bmiCategory, currentDateTime);
            db.bmiDao().insert(bmiResult);
        }).start();
    }

    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd - HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
}