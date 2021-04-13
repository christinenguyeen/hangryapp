package com.example.hangryapp.data;

import com.example.hangryapp.data.PreferenceData;

import java.util.ArrayList;

public class FoodDecider {
    private ArrayList<PreferenceData> preferenceList;

    public PreferenceData getPreference() {
        int random = (int)(Math.random()*this.preferenceList.size());

        return this.preferenceList.get(random);
    }
}
