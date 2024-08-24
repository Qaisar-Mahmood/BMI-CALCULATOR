package com.example.bmicalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerViewBMIHistory;
    private BMIHistoryAdapter adapter;
    private List<BMIResult> bmiResults;
    private Button btnClearAll;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);
       /* ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
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
                    Intent historyIntent = new Intent(HistoryActivity.this, MainActivity.class);
                    startActivity(historyIntent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else if (id==R.id.opt_history){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                return true;
            }
        });

        recyclerViewBMIHistory = findViewById(R.id.recyclerViewBMIHistory);
        btnClearAll = findViewById(R.id.btnClearAll);

        // Setup RecyclerView
        recyclerViewBMIHistory.setLayoutManager(new LinearLayoutManager(this));

        // Load data from Room Database
        loadBMIResults();

        // Handle Clear All Button
        btnClearAll.setOnClickListener(v -> {
            clearAllBMIResults();
        });
    }

    private void loadBMIResults() {
        // Load BMI results from Room Database
        new Thread(() -> {
            BMIDatabase db = BMIDatabase.getInstance(this);
            bmiResults = db.bmiDao().getAllResults();

            runOnUiThread(() -> {
                // Initialize the adapter with fetched data
                adapter = new BMIHistoryAdapter(bmiResults);
                recyclerViewBMIHistory.setAdapter(adapter);
            });
        }).start();
    }

    private void clearAllBMIResults() {
        new Thread(() -> {
            BMIDatabase db = BMIDatabase.getInstance(this);
            db.bmiDao().deleteAll();

            // Clear the list and update the adapter
            runOnUiThread(() -> {
                bmiResults.clear();
                adapter.notifyDataSetChanged();
            });
        }).start();
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

