package com.example.magicplacefinder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.magicplacefinder.R;
import com.example.magicplacefinder.models.responses.PlaceResponse;

import java.util.ArrayList;
import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {

    private ArrayList<PlaceResponse> mListOfPlaces;
    private Context mContext;

    //construct a constructor that takes a list of recipeItems
    public PlacesAdapter(Context c, ArrayList<PlaceResponse> listOfRecipes) {
        mListOfPlaces = listOfRecipes;
        mContext = c;
    }

    @Override
    public PlacesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the item Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.places_list_item, parent,
                false);
        //pass the view to the ViewHolder
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PlacesAdapter.ViewHolder holder, int position) {
        PlaceResponse place = mListOfPlaces.get(position);
        holder.nameTv.setText(mContext.getResources().getString(R.string.name, place.getResult().getName()));
        holder.openingHrsTv.setText(mContext.getResources().getString(R.string.opening_hours, place.getResult().getOpeningHours()));
        holder.ratingTv.setText(mContext.getResources().getString(R.string.rating, String.valueOf(place.getResult().getRating())));
    }

    @Override
    public int getItemCount() {
        return mListOfPlaces.size();
    }

    public void updateAdapter(ArrayList<PlaceResponse> foundPlacesList){
        mListOfPlaces = foundPlacesList;
        notifyDataSetChanged();
    }


    //create viewholder class
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv;
        TextView openingHrsTv;
        TextView ratingTv;

        private ViewHolder(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.name_tv);
            openingHrsTv = itemView.findViewById(R.id.openinghrs_tv);
            ratingTv = itemView.findViewById(R.id.rating_tv);
        }
    }
}

