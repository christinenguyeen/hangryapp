package com.example.hangryapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PreferenceDao {
    @Query("Select * from preferences")
    LiveData<List<PreferenceData>> getAllPreferences();

    @Insert
    void insertAll(PreferenceData... preferenceData);

    @Insert
    void insertPreference(PreferenceData preferenceData);

    @Update
    void updatePreference(PreferenceData preferenceData);

    @Delete
    void deletePreference(PreferenceData preferenceData);
}
