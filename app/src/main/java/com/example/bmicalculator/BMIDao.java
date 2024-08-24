package com.example.bmicalculator;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;

import java.util.List;

@Dao
public interface BMIDao {

    @Insert
    void insert(BMIResult bmiResult);

    @Query("SELECT * FROM bmi_results ORDER BY dateTime DESC")
    List<BMIResult> getAllResults();

    @Query("DELETE FROM bmi_results")
    void deleteAll();
}
