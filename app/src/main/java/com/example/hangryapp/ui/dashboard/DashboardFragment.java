package com.example.hangryapp.ui.dashboard;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
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
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class DashboardFragment extends Fragment {

    protected PreferenceListAdapter listAdapter;
    protected RecyclerView recyclerView;
    protected EditText editTextEnterPreference;
    protected ExtendedFloatingActionButton fabAddNewPreference;
    protected ApplicationViewModel appViewModel;
    //private LinearLayoutManager layoutManager;
    private boolean editMode;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        editMode = false;

        //ViewModel
        appViewModel = new ViewModelProvider(this.requireActivity()).get(ApplicationViewModel.class);
        /*appViewModel.getPreferenceList().observe(getViewLifecycleOwner(), new Observer<List<PreferenceData>>() {
            @Override
            public void onChanged(List<PreferenceData> preferenceData) {
                listAdapter.notifyDataSetChanged();
            }
        });*/

        //Preference List
        recyclerView = root.findViewById(R.id.preference_list2);
        listAdapter = new PreferenceListAdapter(getContext(), appViewModel);
        recyclerView.setAdapter(listAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        /*
         * ---------------Adding New Preferences------------------
         */

        //editText
        editTextEnterPreference = root.findViewById(R.id.editTextEnterPreference);
        editTextEnterPreference.setVisibility(View.GONE);
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
        fabAddNewPreference.setOnClickListener(view -> {
            if(!editMode || TextUtils.isEmpty(editTextEnterPreference.getText())){
                changeEditMode(view);
            }
            else {
                addNewPreference();
            }
            //Navigation.findNavController(view).navigate(R.id.action_open_add_preference);
        });

        //custom back button
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(editMode) {
                    changeEditMode(requireView());
                }
                else {
                    Navigation.findNavController(requireView()).navigateUp();
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this.getViewLifecycleOwner(), callback);

        return root;
    }

    private boolean addNewPreference(){
        String preferenceName = editTextEnterPreference.getText().toString();

        if(preferenceName!=null && !preferenceName.equals("") && !preferenceName.equals(" ")){
            PreferenceData preferenceData = new PreferenceData(preferenceName);
            /*appViewModel.insertPreference(preferenceData);
            listAdapter.notifyItemInserted(listAdapter.getItemCount());*/
            listAdapter.newItem(preferenceData);
            editTextEnterPreference.getText().clear();
            fabAddNewPreference.setIconResource(R.drawable.ic_baseline_check_24);
            fabAddNewPreference.setText(getText(R.string.finish_prefs));
            /*LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            layoutManager.scrollToPositionWithOffset(listAdapter.getItemCount()+1, 0);*/
            recyclerView.scrollToPosition(listAdapter.getItemCount() - 1);
            /*listAdapter.notifyItemInserted(0);
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            layoutManager.scrollToPositionWithOffset(0, 0);*/
            //editTextEnterPreference.requestFocus();

            return true;
        }

        return false;
    }

    private void changeEditMode(View view){
        BottomNavigationView navBar = requireActivity().findViewById(R.id.nav_view);
        Animation bottomUp = AnimationUtils.loadAnimation(getContext(),
                R.anim.bottom_up);
        Animation bottomDown = AnimationUtils.loadAnimation(getContext(),
                R.anim.bottom_down);

        if(editMode) {
            editMode = false;
            closeKeyboard(view);
            //fabAddNewPreference.setImageResource(R.drawable.ic_baseline_edit_24);
            fabAddNewPreference.setIconResource(R.drawable.ic_baseline_edit_24);
            fabAddNewPreference.setText(getText(R.string.edit_prefs));
            slideUp(editTextEnterPreference);
            navBar.startAnimation(bottomUp);
            navBar.setVisibility(View.VISIBLE);
        }
        else {
            editMode = true;
            //fabAddNewPreference.setImageResource(R.drawable.ic_baseline_check_24);
            fabAddNewPreference.setIconResource(R.drawable.ic_baseline_check_24);
            fabAddNewPreference.setText(getText(R.string.finish_prefs));
            slideDown(editTextEnterPreference);
            navBar.startAnimation(bottomDown);
            navBar.setVisibility(View.GONE);
            editTextEnterPreference.requestFocus();
            openKeyboard(view);
        }

        listAdapter.setEditMode(editMode);
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

    public static void slideDown(final View view) {
        view.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = 1;
        view.setLayoutParams(layoutParams);

        view.measure(View.MeasureSpec.makeMeasureSpec(Resources.getSystem().getDisplayMetrics().widthPixels,
                View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED));

        final int height = view.getMeasuredHeight();
        ValueAnimator valueAnimator = ObjectAnimator.ofInt(1, height);
        valueAnimator.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams1 = view.getLayoutParams();
            if (height > value) {
                layoutParams1.height = value;
            }else{
                layoutParams1.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            }
            view.setLayoutParams(layoutParams1);
        });
        valueAnimator.start();
    }

    //custom animation
    public void slideUp(final View view) {
        view.post(() -> {
            final int height = view.getHeight();
            ValueAnimator valueAnimator = ObjectAnimator.ofInt(height, 0);
            valueAnimator.addUpdateListener(animation -> {
                int value = (int) animation.getAnimatedValue();
                if (value > 0) {
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    layoutParams.height = value;
                    view.setLayoutParams(layoutParams);
                }else{
                    view.setVisibility(View.GONE);
                    editTextEnterPreference.getText().clear();
                }
            });
            valueAnimator.start();
        });

    }
}

/*
EditText etUserName = (EditText) findViewById(R.id.txtUsername);
String strUserName = etUserName.getText().toString();

 if(TextUtils.isEmpty(strUserName)) {
    etUserName.setError("Your message");
    return;
 }
 */

        /*editTextEnterPreference.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(entered && mode==1){
                    editTextEnterPreference.requestFocus();
                    entered = false;
                }
            }
        });*/
        /*editTextEnterPreference.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE){
                    addNewPreference();
                }
                return false;
            }
        });*/

        /*editTextEnterPreference.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN && i==KeyEvent.KEYCODE_ENTER) {
                    entered = true;
                    return addNewPreference();
                }
                return false;
            }
        });*/

                /*else if(keyCode != KeyEvent.KEYCODE_DEL){
                    fabAddNewPreference.setIconResource(R.drawable.ic_baseline_add_24);
                    fabAddNewPreference.setText(getText(R.string.add_prefs));
                }
                else if(TextUtils.isEmpty(editTextEnterPreference.getText()) || editTextEnterPreference.getText().toString().length()==1){
                    fabAddNewPreference.setIconResource(R.drawable.ic_baseline_check_24);
                    fabAddNewPreference.setText(getText(R.string.finish_prefs));
                }*/
                /*else if(TextUtils.isEmpty(editTextEnterPreference.getText()) && keyCode == KeyEvent.KEYCODE_DEL){
                    fabAddNewPreference.setIconResource(R.drawable.ic_baseline_check_24);
                    fabAddNewPreference.setText(getText(R.string.finish_prefs));
                }
                else {
                    fabAddNewPreference.setIconResource(R.drawable.ic_baseline_add_24);
                    fabAddNewPreference.setText(getText(R.string.add_prefs));
                }*/