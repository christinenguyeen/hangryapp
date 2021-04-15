package com.example.hangryapp.ui.dashboard;

import android.content.Context;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.hangryapp.ApplicationViewModel;
import com.example.hangryapp.R;
import com.example.hangryapp.data.PreferenceData;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class AddPreferenceFragment extends DashboardFragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        //ViewModel
        appViewModel = new ViewModelProvider(this.requireActivity()).get(ApplicationViewModel.class);
        appViewModel.getPreferenceList().observe(getViewLifecycleOwner(), preferenceData -> listAdapter.notifyDataSetChanged());

        //Preference List
        recyclerView = root.findViewById(R.id.preference_list);
        listAdapter = new PreferenceListAdapter(getContext(), appViewModel);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //editText
        editTextEnterPreference = root.findViewById(R.id.editTextEnterPreference);
        slideDown(editTextEnterPreference);
        editTextEnterPreference.requestFocus();
        openKeyboard(editTextEnterPreference);
        //editTextEnterPreference.setVisibility(View.GONE);
        editTextEnterPreference.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                if (event.getAction() == KeyEvent.ACTION_DOWN){
                   addNewPreference();
                }
                return true;
            }
            return false;
        });

        //fab
        fabAddNewPreference = root.findViewById(R.id.fabAddNewPreference);
        //fabAddNewPreference.setImageResource(R.drawable.ic_baseline_check_24);
        fabAddNewPreference.setIconResource(R.drawable.ic_baseline_check_24);
        fabAddNewPreference.setOnClickListener(view -> {
            //changeEditMode(view);
            closeKeyboard(view);
            Navigation.findNavController(view).navigateUp();
        });

        BottomNavigationView navBar = requireActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.GONE);

        return root;
    }

    private boolean addNewPreference(){
        String preferenceName = editTextEnterPreference.getText().toString();

        if(preferenceName!=null && !preferenceName.equals("") && !preferenceName.equals(" ")){
            PreferenceData preferenceData = new PreferenceData(preferenceName);
            appViewModel.insertPreference(preferenceData);
            listAdapter.notifyDataSetChanged();
            editTextEnterPreference.getText().clear();
            listAdapter.notifyItemInserted(listAdapter.getItemCount());
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            layoutManager.scrollToPositionWithOffset(listAdapter.getItemCount(), 0);
            //editTextEnterPreference.requestFocus();

            return true;
        }

        return false;
    }

    private void closeKeyboard(View view) {
        if (view != null) {
            InputMethodManager manager;
            manager = (InputMethodManager) requireActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected void openKeyboard(View view) {
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) requireActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }
}