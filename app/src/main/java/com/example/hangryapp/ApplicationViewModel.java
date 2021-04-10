package com.example.hangryapp;

import android.app.Application;
import android.preference.Preference;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.hangryapp.data.DataRepository;
import com.example.hangryapp.data.PreferenceData;

import java.util.List;

public class ApplicationViewModel extends AndroidViewModel {
    private DataRepository mRepository;
    private final LiveData<List<PreferenceData>> preferenceList;

    public ApplicationViewModel(@NonNull Application application){
        super(application);
        mRepository = new DataRepository(application);
        preferenceList = mRepository.getAllPreferences();
    }

    public LiveData<List<PreferenceData>> getPreferenceList(){
        return preferenceList;
        //return mRepository.getAllPreferences();
    }

    public void insertPreference(PreferenceData preferenceData){
        mRepository.insertPreference(preferenceData);
    }

    public void updatePreference(PreferenceData preferenceData){
        mRepository.updatePreference(preferenceData);
    }

    public void deletePreference(PreferenceData preferenceData){
        mRepository.deletePreference(preferenceData);
    }
}
