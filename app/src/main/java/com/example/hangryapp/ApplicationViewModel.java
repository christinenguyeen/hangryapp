package com.example.hangryapp;

import android.app.Application;
import android.preference.Preference;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.hangryapp.data.DataRepository;
import com.example.hangryapp.data.PreferenceData;

import java.util.List;

public class ApplicationViewModel extends AndroidViewModel {
    private DataRepository mRepository;
    private final LiveData<List<PreferenceData>> preferenceList;
    //private Boolean foodChosen;
    private final MutableLiveData<String> foodChosen;

    public ApplicationViewModel(@NonNull Application application){
        super(application);
        mRepository = new DataRepository(application);
        preferenceList = mRepository.getAllPreferences();
        foodChosen = new MutableLiveData<>();
    }

    public LiveData<List<PreferenceData>> getPreferenceList(){
        return preferenceList;
        //return mRepository.getAllPreferences();
    }

    public void insertPreference(PreferenceData preferenceData){
        mRepository.insertPreference(preferenceData);
    }

    public void insertPreferences(List<PreferenceData> preferenceData){
        mRepository.insertPreferences(preferenceData);
    }

    public void updatePreference(PreferenceData preferenceData){
        mRepository.updatePreference(preferenceData);
    }

    public void deletePreference(PreferenceData preferenceData){
        mRepository.deletePreference(preferenceData);
    }

    public void setFoodChosen(String food){
        foodChosen.setValue(food);
    }

    public String getFoodChosen(){
        return foodChosen.getValue();
    }

    /*public void foodHasBeenChosen(){
        foodChosen = true;
    }

    public Boolean isFoodChosen(){
        return foodChosen;
    }*/
}
