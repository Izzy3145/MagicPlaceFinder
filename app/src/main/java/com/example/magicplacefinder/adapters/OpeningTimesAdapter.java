package com.example.magicplacefinder.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.magicplacefinder.R;

import java.util.ArrayList;

public class OpeningTimesAdapter extends RecyclerView.Adapter<OpeningTimesAdapter.ViewHolder> {

    private ArrayList<String> mWeekdayStrings;
    private Context mContext;

    public OpeningTimesAdapter(Context c,  ArrayList<String> weekdayStrings) {
        mWeekdayStrings = new ArrayList<>(weekdayStrings);
        mContext = c;
    }

    @Override
    public OpeningTimesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.opening_hrs_list_item, parent,
                false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OpeningTimesAdapter.ViewHolder holder, int position) {
        String weekdayString = mWeekdayStrings.get(position);
        if(!weekdayString.equals("") || !TextUtils.isEmpty(weekdayString)) {
            holder.openingHrsTv.setText(weekdayString);
        } else {
            holder.openingHrsTv.setText(mContext.getResources().getString(R.string.unavailable));
        }
    }

    @Override
    public int getItemCount() {
        return mWeekdayStrings.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView openingHrsTv;

        private ViewHolder(View itemView) {
            super(itemView);
            openingHrsTv = itemView.findViewById(R.id.day_opening_hrs_tv);
        }
    }
}
