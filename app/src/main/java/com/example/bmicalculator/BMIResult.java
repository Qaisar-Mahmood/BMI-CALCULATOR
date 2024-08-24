package com.example.bmicalculator;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bmi_results")
public class BMIResult {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public double bmiValue;
    public String bmiCategory;
    public String dateTime;

    public BMIResult(double bmiValue, String bmiCategory, String dateTime) {
        this.bmiValue = bmiValue;
        this.bmiCategory = bmiCategory;
        this.dateTime = dateTime;
    }
}
