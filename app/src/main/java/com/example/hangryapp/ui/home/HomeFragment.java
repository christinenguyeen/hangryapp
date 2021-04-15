package com.example.hangryapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.hangryapp.ApplicationViewModel;
import com.example.hangryapp.R;
import com.example.hangryapp.data.PreferenceData;
import com.example.hangryapp.ui.mapsAndLocation.MapsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    //private Boolean foodChosen;
    private ApplicationViewModel appViewModel;
    private View mapsFragment;
    private CardView cardViewFoodChosen;
    private TextView textFoodChosen;
    private String foodChosen;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //System.out.println("View Created! Boolean is "+foodChosen);

        //ViewModel
        appViewModel = new ViewModelProvider(this).get(ApplicationViewModel.class);
        appViewModel.getPreferenceList().observe(getViewLifecycleOwner(), preferenceData -> {

        });

        //food info
        textFoodChosen = root.findViewById(R.id.textFoodChosen);
        mapsFragment = root.findViewById(R.id.fragment_maps_host);
        cardViewFoodChosen = root.findViewById(R.id.cardViewFoodChosen);
        foodChosen = appViewModel.getFoodChosen();

        if(foodChosen==null) {
            mapsFragment.setVisibility(View.GONE);
            cardViewFoodChosen.setVisibility(View.GONE);
        }
        else {
            textFoodChosen.setText(foodChosen);
        }


        //fab
        FloatingActionButton fabChoose = root.findViewById(R.id.fabChoose);
        fabChoose.setOnClickListener(view -> {
            showItems();
            selectPreference();
            /*String[] list;
            // converts user preference list to array
            list = appViewModel.getPreferenceList().getValue().toString().split("[\n\t\r.,;:!?(){]");
            // removes all special characters from array
            for (int i = 0; i < list.length; i++) {
                list[i] = list[i].replaceAll("\\[", "").replaceAll("\\]","");
            }
            // randomly chooses food from array
            int random = (int)(Math.random()*list.length);
            foodChosen = list[random];
            appViewModel.setFoodChosen(foodChosen);
            textFoodChosen.setText(foodChosen);*/
        });
      
        return root;
    }

    private void selectPreference(){
        List<PreferenceData> preferenceList =  appViewModel.getPreferenceList().getValue();
        if(preferenceList!=null && preferenceList.size()!=0) {
            int random = (int) (Math.random() * preferenceList.size());
            foodChosen = preferenceList.get(random).getName();
        }
        else {
            foodChosen = "No Preferences to Choose From";
        }
        appViewModel.setFoodChosen(foodChosen);
        textFoodChosen.setText(foodChosen);
    }

    private void showItems(){
        Animation showAnim = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        cardViewFoodChosen.startAnimation(showAnim);
        cardViewFoodChosen.setVisibility(View.VISIBLE);
        mapsFragment.startAnimation(showAnim);
        mapsFragment.setVisibility(View.VISIBLE);
    }


    /*Override
    public void onDestroyView () {
        System.out.println("View Destroyed! Boolean is "+foodChosen);
        if(foodChosen) {
            super.onDestroyView();
            foodChosen = true;
        }
        else {
            super.onDestroyView();
        }
    }*/

    /*@Override
    public void onResume (){
        System.out.println("View Resumed!");
        if (foodChosen){
            showItems();
        }
        super.onResume();
    }

    @Override
    public void onStart(){
        System.out.println("View Started!");
        if (foodChosen){
            showItems();
        }
        super.onStart();
    }*/
}