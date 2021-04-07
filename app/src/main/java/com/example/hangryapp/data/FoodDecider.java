package com.example.hangryapp.data;
import java.util.ArrayList;

public class FoodDecider {
    private ArrayList<PreferenceData> preferenceList;

    public FoodDecider(){}

    public FoodDecider(ArrayList<PreferenceData> preferenceList) {
        this.preferenceList = preferenceList;
    }

    public PreferenceData getPreferenceData() {
        int random = (int)(Math.random()*preferenceList.size());

        return preferenceList.get(random);
    }
}
