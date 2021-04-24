package com.example.hangryapp.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hangryapp.ApplicationViewModel;
import com.example.hangryapp.R;
import com.example.hangryapp.data.PreferenceData;

import java.util.ArrayList;
import java.util.List;

public class PreferenceListAdapter extends RecyclerView.Adapter<PreferenceListAdapter.PreferenceListViewHolder>{

    private Context context;
    private ApplicationViewModel viewModel;
    private boolean editMode;
    private List<PreferenceData> DataSet, newItems;


    public PreferenceListAdapter(Context ct){
        context = ct;
    }

    public PreferenceListAdapter(Context ct, ApplicationViewModel vm){
        context = ct;
        viewModel = vm;
        editMode = false;
        newItems = new ArrayList<>();
        if(viewModel.getPreferenceList().getValue()!=null){
            DataSet = viewModel.getPreferenceList().getValue();
        }
        else {
            DataSet = new ArrayList<>();
        }
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
        holder.pref_name_txt.setText(DataSet.get(position).getName());

        if (editMode){
            holder.delete_pref_button.setVisibility(View.VISIBLE);
            //delete action
            holder.delete_pref_button.setOnClickListener(view -> {
                //viewModel.deletePreference(viewModel.getPreferenceList().getValue().get(position));
                int pos = holder.getAdapterPosition();
                viewModel.deletePreference(DataSet.get(pos));
                newItems.remove(DataSet.get(pos));
                DataSet.remove(pos);
                notifyItemRemoved(pos);
            });
        }
        else{
            holder.delete_pref_button.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return DataSet.size();
    }

    public void setEditMode(boolean mode){
        editMode = mode;
        notifyDataSetChanged();
        if(!editMode){
            //System.out.println("New Items Added!");
            viewModel.insertPreferences(newItems);
            //newItems.clear();
        }
    }

    public void newItem(PreferenceData preferenceData){
        //viewModel.insertPreference(preferenceData);
        DataSet.add(preferenceData);
        newItems.add(preferenceData);
        notifyItemInserted(getItemCount());
    }

    public class PreferenceListViewHolder extends RecyclerView.ViewHolder{
        private final TextView pref_name_txt;
        private final ImageButton delete_pref_button;

        public PreferenceListViewHolder(@NonNull View itemView) {
            super(itemView);
            pref_name_txt = itemView.findViewById(R.id.pref_name_txt);
            delete_pref_button = itemView.findViewById(R.id.delete_pref_button);
        }
    }
}
