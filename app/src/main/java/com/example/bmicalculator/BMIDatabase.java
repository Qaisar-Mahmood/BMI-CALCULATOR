package com.example.bmicalculator;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {BMIResult.class}, version = 1)
public abstract class BMIDatabase extends RoomDatabase {

    private static BMIDatabase instance;

    public abstract BMIDao bmiDao();

    public static synchronized BMIDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            BMIDatabase.class, "bmi_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
