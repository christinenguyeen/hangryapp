package com.example.hangryapp.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hangryapp.ApplicationViewModel;
import com.example.hangryapp.R;
import com.example.hangryapp.data.PreferenceData;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class DashboardFragment extends Fragment {

    private PreferenceListAdapter listAdapter;
    private RecyclerView recyclerView;
    private EditText editTextEnterPreference;
    private ApplicationViewModel appViewModel;
    private int mode;
    private boolean entered;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        mode = 0;

        //ViewModel
        appViewModel = new ViewModelProvider(this.requireActivity()).get(ApplicationViewModel.class);
        appViewModel.getPreferenceList().observe(getViewLifecycleOwner(), new Observer<List<PreferenceData>>() {
            @Override
            public void onChanged(List<PreferenceData> preferenceData) {
                listAdapter.notifyDataSetChanged();
            }
        });

        //Preference List
        recyclerView = root.findViewById(R.id.preference_list);
        listAdapter = new PreferenceListAdapter(getContext(), appViewModel);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        /*
         * ---------------Adding New Preferences------------------
         */
        //Animations, will add more
        Animation bottomUp = AnimationUtils.loadAnimation(getContext(),
                R.anim.bottom_up);

        //editText
        editTextEnterPreference = root.findViewById(R.id.editTextEnterPreference);
        editTextEnterPreference.setVisibility(View.GONE);
        editTextEnterPreference.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN && i==KeyEvent.KEYCODE_ENTER) {
                    entered = true;
                    return addNewPreference();
                }
                return false;
            }
        });
        editTextEnterPreference.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(entered && mode==1){
                    editTextEnterPreference.requestFocus();
                    entered = false;
                }
            }
        });
        /*editTextEnterPreference.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE){
                    addNewPreference();
                }
                return false;
            }
        });*/

        //fab
        FloatingActionButton fabAddNewPreference = root.findViewById(R.id.fabAddNewPreference);
        fabAddNewPreference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigation.findNavController(view).navigate(R.id.action_open_add_preference);
                BottomNavigationView navBar = requireActivity().findViewById(R.id.nav_view);

                switch(mode){
                    case 0:
                        mode = 1;
                        fabAddNewPreference.setImageResource(R.drawable.ic_baseline_check_24);
                        editTextEnterPreference.setVisibility(View.VISIBLE);
                        navBar.setVisibility(View.GONE);
                        editTextEnterPreference.requestFocus();
                        openKeyboard(view);
                        break;
                    case 1:
                        mode = 0;
                        editTextEnterPreference.setVisibility(View.GONE);
                        fabAddNewPreference.setImageResource(R.drawable.ic_baseline_edit_24);
                        navBar.setVisibility(View.VISIBLE);
                        closeKeyboard(view);
                }
            }
        });

        return root;
    }

    private boolean addNewPreference(){
        String preferenceName = editTextEnterPreference.getText().toString();

        if(preferenceName!=null && !preferenceName.equals("") && !preferenceName.equals(" ")){
            PreferenceData preferenceData = new PreferenceData(preferenceName);
            appViewModel.insertPreference(preferenceData);
            listAdapter.notifyDataSetChanged();
            editTextEnterPreference.getText().clear();
            //editTextEnterPreference.requestFocus();

            return true;
        }

        return false;
    }

    private void closeKeyboard(View view) {
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) requireActivity()
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