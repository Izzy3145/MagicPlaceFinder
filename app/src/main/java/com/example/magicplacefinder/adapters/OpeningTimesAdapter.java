package com.example.magicplacefinder.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.magicplacefinder.R;
import com.example.magicplacefinder.models.responses.PlaceResponse;

import java.util.ArrayList;

public class OpeningTimesAdapter extends RecyclerView.Adapter<OpeningTimesAdapter.ViewHolder> {

    private PlaceResponse.Result mResult;
    private Context mContext;

    //construct a constructor that takes a list of recipeItems
    public OpeningTimesAdapter(Context c, PlaceResponse.Result result) {
        mResult = result;
        mContext = c;
    }

    @Override
    public OpeningTimesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the item Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.opening_hrs_list_item, parent,
                false);
        //pass the view to the ViewHolder
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OpeningTimesAdapter.ViewHolder holder, int position) {
        PlaceResponse.Result result = mResult;
        holder.mondayHrsTv.setText(mContext.getResources().getString(R.string.name, result.getName()));
        holder.tuesdayHrsTv.setText(mContext.getResources().getString(R.string.name, result.getName()));
        holder.wednesdayHrsTv.setText(mContext.getResources().getString(R.string.name, result.getName()));
        holder.thursdayHrsTv.setText(mContext.getResources().getString(R.string.name, result.getName()));
        holder.fridayHrsTv.setText(mContext.getResources().getString(R.string.name, result.getName()));
        holder.saturdayHrsTv.setText(mContext.getResources().getString(R.string.name, result.getName()));
        holder.sundayHrsTv.setText(mContext.getResources().getString(R.string.name, result.getName()));

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public void setOpeningTimeData(PlaceResponse.Result result){
        mResult = result;
        notifyDataSetChanged();
    }


    //create viewholder class
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mondayHrsTv;
        TextView tuesdayHrsTv;
        TextView wednesdayHrsTv;
        TextView thursdayHrsTv;
        TextView fridayHrsTv;
        TextView saturdayHrsTv;
        TextView sundayHrsTv;

        private ViewHolder(View itemView) {
            super(itemView);
            mondayHrsTv = itemView.findViewById(R.id.monday_hrs_tv);
            tuesdayHrsTv = itemView.findViewById(R.id.tuesday_hrs_tv);
            wednesdayHrsTv = itemView.findViewById(R.id.wednesday_hrs_tv);
            thursdayHrsTv = itemView.findViewById(R.id.thursday_hrs_tv);
            fridayHrsTv = itemView.findViewById(R.id.friday_hrs_tv);
            saturdayHrsTv = itemView.findViewById(R.id.saturday_hrs_tv);
            sundayHrsTv = itemView.findViewById(R.id.sunday_hrs_tv);
        }
    }
}
