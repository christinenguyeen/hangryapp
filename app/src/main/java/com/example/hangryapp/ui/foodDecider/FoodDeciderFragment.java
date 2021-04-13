package com.example.hangryapp.ui.foodDecider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.hangryapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FoodDeciderFragment extends Fragment {
    private FoodDeciderViewModel foodDeciderViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        foodDeciderViewModel =
                new ViewModelProvider(this).get(FoodDeciderViewModel.class);
        View root = inflater.inflate(R.layout.fragment_food_decider, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        foodDeciderViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }
}
