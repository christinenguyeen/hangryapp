package com.example.hangryapp.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hangryapp.ApplicationViewModel;
import com.example.hangryapp.R;

public class PreferenceListAdapter extends RecyclerView.Adapter<PreferenceListAdapter.PreferenceListViewHolder>{

    private Context context;
    private ApplicationViewModel viewModel;

    public PreferenceListAdapter(Context ct){
        context = ct;
    }

    public PreferenceListAdapter(Context ct, ApplicationViewModel vm){
        context = ct;
        viewModel = vm;
    }

    @NonNull
    @Override
    public PreferenceListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.preference_row, parent, false);
        return new PreferenceListAdapter.PreferenceListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PreferenceListViewHolder holder, int position) {
        //holder.pref_name_txt.setText(R.string.food_name);
        holder.pref_name_txt.setText(viewModel.getPreferenceList().getValue().get(position).getName());
    }

    @Override
    public int getItemCount() {
        //try {
            return viewModel.getPreferenceList().getValue().size();
        /*}
        catch (NullPointerException e){
            boolean observers = viewModel.getPreferenceList().hasActiveObservers();
            System.out.println("ViewModel has observers? "+observers);
            /*if(observers) {
                viewModel.getPreferenceList().notify();
            }
            return 0;
        }*/
        //return 0;
    }

    public class PreferenceListViewHolder extends RecyclerView.ViewHolder{
        private final TextView pref_name_txt;

        public PreferenceListViewHolder(@NonNull View itemView) {
            super(itemView);
            pref_name_txt = itemView.findViewById(R.id.pref_name_txt);
        }
    }
}
