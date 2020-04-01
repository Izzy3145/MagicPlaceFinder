package com.example.magicplacefinder.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.magicplacefinder.R;
import com.example.magicplacefinder.models.responses.PlaceResponse;

import java.util.ArrayList;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {

    private ArrayList<PlaceResponse> mListOfPlaces;
    private Context mContext;

    public PlacesAdapter(Context c, ArrayList<PlaceResponse> listOfRecipes) {
        mListOfPlaces = listOfRecipes;
        mContext = c;
    }

    @Override
    public PlacesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.places_list_item, parent,
                false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PlacesAdapter.ViewHolder holder, int position) {
        PlaceResponse place = mListOfPlaces.get(position);
        holder.nameTv.setText(showUnavailableOrAvailableData(place.getResult().getName(),
                mContext.getResources().getString(R.string.name, place.getResult().getName())));
        if(place.getResult().getOpeningHours() != null){
            holder.openingHrsRv.setVisibility(View.VISIBLE);
            holder.openingHrsTv.setVisibility(View.GONE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
            holder.openingHrsRv.setLayoutManager(layoutManager);
            holder.openingHrsRv.setHasFixedSize(true);
            ArrayList<String> weekdayStrings = new ArrayList<>(place.getResult().getOpeningHours().getWeekdayText());
            OpeningTimesAdapter adapter = new OpeningTimesAdapter(mContext, weekdayStrings);
            holder.openingHrsRv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            holder.openingHrsRv.setVisibility(View.GONE);
            holder.openingHrsTv.setVisibility(View.VISIBLE);
            holder.openingHrsTv.setText(mContext.getResources().getString(R.string.opening_hours,
                    mContext.getResources().getString(R.string.unavailable)));
        }


        if(place.getResult().getRating() != null) {
            holder.ratingTv.setText(showUnavailableOrAvailableData(String.valueOf(place.getResult().getRating()),
                    mContext.getResources().getString(R.string.name, String.valueOf(place.getResult().getRating()))));
            holder.ratingBar.setRating(place.getResult().getRating().floatValue());
            holder.ratingBar.setVisibility(View.VISIBLE);
        } else {
            holder.ratingTv.setText(mContext.getResources().getString(R.string.rating,
                    mContext.getResources().getString(R.string.unavailable)));
            holder.ratingBar.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mListOfPlaces.size();
    }

    public void updateAdapter(ArrayList<PlaceResponse> foundPlacesList){
        mListOfPlaces = foundPlacesList;
        notifyDataSetChanged();
    }

    private String showUnavailableOrAvailableData(String stringToTest, String availableString){
        if(stringToTest == null ||stringToTest.equals("") || TextUtils.isEmpty(stringToTest)){
            return mContext.getResources().getString(R.string.unavailable);
        } else {
            return availableString;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv;
        TextView openingHrsTv;
        RecyclerView openingHrsRv;
        TextView ratingTv;
        RatingBar ratingBar;

        private ViewHolder(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.name_tv);
            openingHrsTv = itemView.findViewById(R.id.openinghrs_tv);
            openingHrsRv = itemView.findViewById(R.id.openinghrs_rv);
            ratingTv = itemView.findViewById(R.id.rating_tv);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}

