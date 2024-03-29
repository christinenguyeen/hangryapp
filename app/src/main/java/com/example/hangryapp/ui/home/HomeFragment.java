package com.example.hangryapp.ui.home;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    //private Boolean foodChosen;
    private ApplicationViewModel appViewModel;
    private View mapsFragment;
    private CardView cardViewFoodChosen;
    private TextView textFoodChosen;
    private ExtendedFloatingActionButton fabOpenMaps;
    private String foodChosen;
    private Integer lastIndex;

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
        fabOpenMaps = root.findViewById(R.id.fabOpenMaps);
        fabOpenMaps.setOnClickListener(view -> {
            try {
                if (foodChosen != null) {
                    String url = "http://maps.google.co.in/maps?q=" + foodChosen;
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(intent);
                }
            }
            catch (ActivityNotFoundException e){
                Toast toast = Toast.makeText(requireContext(), "Could not open Google Maps!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        foodChosen = appViewModel.getFoodChosen();
        if(foodChosen==null) {
            mapsFragment.setVisibility(View.GONE);
            cardViewFoodChosen.setVisibility(View.GONE);
            fabOpenMaps.setVisibility(View.GONE);
        }
        else {
            textFoodChosen.setText(foodChosen);
        }


        //fab
        FloatingActionButton fabChoose = root.findViewById(R.id.fabChoose);
        fabChoose.setOnClickListener(view -> {
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
        Animation showAnim = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);

        if(preferenceList!=null && preferenceList.size()!=0) {
            int random = (int) (Math.random() * preferenceList.size());

            if(lastIndex!=null && preferenceList.size()!=1){
                while(lastIndex==random){
                    random = (int) (Math.random() * preferenceList.size());
                }
            }
            foodChosen = preferenceList.get(random).getName();
            lastIndex = random;

            if(mapsFragment.getVisibility()==View.GONE) {
                mapsFragment.startAnimation(showAnim);
                mapsFragment.setVisibility(View.VISIBLE);
            }
            if(fabOpenMaps.getVisibility()==View.GONE) {
                fabOpenMaps.startAnimation(showAnim);
                fabOpenMaps.setVisibility(View.VISIBLE);
            }
        }
        else {
            foodChosen = "No Preferences to Choose From";
        }
        appViewModel.setFoodChosen(foodChosen);
        textFoodChosen.setText(foodChosen);
        cardViewFoodChosen.startAnimation(showAnim);
        cardViewFoodChosen.setVisibility(View.VISIBLE);
    }

    private void showItems(){
        Animation showAnim = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        cardViewFoodChosen.startAnimation(showAnim);
        cardViewFoodChosen.setVisibility(View.VISIBLE);
        mapsFragment.startAnimation(showAnim);
        mapsFragment.setVisibility(View.VISIBLE);
        fabOpenMaps.startAnimation(showAnim);
        fabOpenMaps.setVisibility(View.VISIBLE);
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