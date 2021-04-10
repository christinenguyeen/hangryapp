package com.example.hangryapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.hangryapp.ApplicationViewModel;
import com.example.hangryapp.R;
import com.example.hangryapp.data.PreferenceData;
import com.example.hangryapp.ui.mapsAndLocation.MapsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //ViewModel
        ApplicationViewModel appViewModel = new ViewModelProvider(this).get(ApplicationViewModel.class);
        appViewModel.getPreferenceList().observe(getViewLifecycleOwner(), new Observer<List<PreferenceData>>() {
            @Override
            public void onChanged(List<PreferenceData> preferenceData) {

            }
        });

        View mapsFragment = root.findViewById(R.id.fragment_maps_host);
        mapsFragment.setVisibility(View.GONE);

        TextView textFoodChosen = root.findViewById(R.id.textFoodChosen);

        CardView cardViewFoodChosen = root.findViewById(R.id.cardViewFoodChosen);
        cardViewFoodChosen.setVisibility(View.GONE);

        FloatingActionButton fabChoose = root.findViewById(R.id.fabChoose);
        fabChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapsFragment.setVisibility(View.VISIBLE);
                textFoodChosen.setText("Food Choice Here");
                cardViewFoodChosen.setVisibility(View.VISIBLE);
            }
        });
      
        return root;
    }
}