package com.example.bmicalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
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

public class MainActivity extends AppCompatActivity {

    // Default Values
    private int age= 19;
    private int weight= 74;
    private int height= 180;
    private String gender= "Male";

    LinearLayout layoutMale, layoutFemale;
    TextView tvHeight, tvAge, tvWeight;
    Button btnDecreaseAge, btnIncreaseAge, btnDecreaseWeight, btnIncreaseWeight, btnCalculateBMI;
    SeekBar seekBarHeight;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

        layoutMale= findViewById(R.id.layoutMale);
        layoutFemale= findViewById(R.id.layoutFemale);
        tvHeight= findViewById(R.id.tvHeight);
        tvAge= findViewById(R.id.tvAge);
        tvWeight= findViewById(R.id.tvWeight);
        btnDecreaseAge= findViewById(R.id.btnDecreaseAge);
        btnIncreaseAge= findViewById(R.id.btnIncreaseAge);
        btnDecreaseWeight= findViewById(R.id.btnDecreaseWeight);
        btnIncreaseWeight= findViewById(R.id.btnIncreaseWeight);
        btnCalculateBMI= findViewById(R.id.btnCalculateBMI);
        seekBarHeight= findViewById(R.id.seekBarHeight);
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
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else if (id==R.id.opt_history){
                    Intent historyIntent = new Intent(MainActivity.this, HistoryActivity.class);
                    startActivity(historyIntent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                return true;
            }
        });


        //Set Initial Values
        updateGenderHighlight();
        seekBarHeight.setProgress(height);
        tvHeight.setText(height+" cm");
        tvAge.setText(String.valueOf(age));
        tvWeight.setText(String.valueOf(weight));

        //Gender Selection
        layoutMale.setOnClickListener(v -> {
            gender= "Male";
            updateGenderHighlight();
        });

        layoutFemale.setOnClickListener(v -> {
            gender= "Female";
            updateGenderHighlight();
        });

        //Age Selection
        btnDecreaseAge.setOnClickListener(v -> {
            if (age>0){
                age--;
                tvAge.setText(String.valueOf(age));
            }
            else {
                Toast.makeText(MainActivity.this, "Age Cannot Be Less Than 0", Toast.LENGTH_LONG).show();
            }
        });

        btnIncreaseAge.setOnClickListener(v -> {
            age++;
            tvAge.setText(String.valueOf(age));
        });

        //Weight Selection
        btnDecreaseWeight.setOnClickListener(v -> {
            if (weight>0){
                weight--;
                tvWeight.setText(String.valueOf(weight));
            }
            else {
                Toast.makeText(MainActivity.this, "Weight Cannot Be Less Than 0", Toast.LENGTH_LONG).show();
            }
        });

        btnIncreaseWeight.setOnClickListener(v -> {
            weight++;
            tvWeight.setText(String.valueOf(weight));
        });

        //Select Height
        seekBarHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                height = progress;
                tvHeight.setText(height+" cm");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }
        });

        //Calculate BMI
        btnCalculateBMI.setOnClickListener(v -> {
            if (height > 0) {
                double heightInMeters= height / 100.0;
                double bmi= weight / (heightInMeters * heightInMeters);

                String bmiCategory;
                String advice;
                if (bmi<18.5) {
                    bmiCategory= "Underweight";
                    advice= "You should eat more and exercise to gain weight.";
                }
                else if (bmi>=18.5 && bmi<24.9) {
                    bmiCategory= "Healthy";
                    advice= "Keep up the good work! Maintain a healthy lifestyle.";
                }
                else if (bmi>=25 && bmi<29.9) {
                    bmiCategory= "Overweight";
                    advice= "The best way to lose weight if you are overweight is through a combination of diet and exercise.";
                }
                else {
                    bmiCategory= "Obese";
                    advice= "It is important to consult with a healthcare provider for advice on how to achieve a healthier weight.";
                }

                // Start ResultActivity and pass data
                Intent intent= new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("BMI_VALUE", bmi);
                intent.putExtra("BMI_CATEGORY", bmiCategory);
                intent.putExtra("ADVICE", advice);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Height must be greater than 0", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void updateGenderHighlight(){
        if (gender.equals("Male")){
            layoutMale.setBackgroundColor(getResources().getColor(R.color.layout_selected_gender_BG));
            layoutFemale.setBackgroundColor(getResources().getColor(R.color.layout_unselected_gender_BG));
        }
        else {
            layoutMale.setBackgroundColor(getResources().getColor(R.color.layout_unselected_gender_BG));
            layoutFemale.setBackgroundColor(getResources().getColor(R.color.layout_selected_gender_BG));
        }
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