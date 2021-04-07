package com.example.hangryapp.ui.foodDecider;

import com.example.hangryapp.data.PreferenceData;

import java.util.ArrayList;

public class FoodDeciderFragment {
    private ArrayList<PreferenceData> preferenceList;

    public PreferenceData getPreference() {
        int random = (int)(Math.random()*this.preferenceList.size());

        return this.preferenceList.get(random);
    }
}
