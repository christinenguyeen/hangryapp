package com.example.hangryapp.ui.foodDecider;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FoodDeciderViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FoodDeciderViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is food decider fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}