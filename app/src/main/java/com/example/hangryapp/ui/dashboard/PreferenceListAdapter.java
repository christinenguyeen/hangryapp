package com.example.hangryapp.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hangryapp.R;

public class PreferenceListAdapter extends RecyclerView.Adapter<PreferenceListAdapter.PreferenceListViewHolder>{

    private Context context;

    public PreferenceListAdapter(Context ct){
        context = ct;
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
        holder.pref_name_txt.setText(R.string.food_name);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class PreferenceListViewHolder extends RecyclerView.ViewHolder{
        private final TextView pref_name_txt;

        public PreferenceListViewHolder(@NonNull View itemView) {
            super(itemView);
            pref_name_txt = itemView.findViewById(R.id.pref_name_txt);
        }
    }
}
