package com.example.hangryapp.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DataRepository {
    private PreferenceDao preferenceDao;
    private LiveData<List<PreferenceData>> allPreferences;

    public DataRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        preferenceDao = db.preferenceDao();
        allPreferences = preferenceDao.getAllPreferences();
    }

    public LiveData<List<PreferenceData>> getAllPreferences() {
        return allPreferences;
    }

    public void insertPreference(PreferenceData preferenceData) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            preferenceDao.insertPreference(preferenceData);
        });
    }

    public void updatePreference(PreferenceData preferenceData) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            preferenceDao.updatePreference(preferenceData);
        });
    }

    public void deletePreference(PreferenceData preferenceData) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            preferenceDao.deletePreference(preferenceData);
        });
    }
}
