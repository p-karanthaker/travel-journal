package me.karanthaker.traveljournal.me.karanthaker.traveljournal.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import me.karanthaker.traveljournal.OpenPlace;
import me.karanthaker.traveljournal.R;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Place;

/**
 * Created by KTHAKER on 27/02/2018.
 */

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.PlaceViewHolder> {

    class PlaceViewHolder extends RecyclerView.ViewHolder {
        private final TextView holidayNameView;
        private final TextView holidayStartDateView;

        private PlaceViewHolder(View itemView) {
            super(itemView);
            holidayNameView = itemView.findViewById(R.id.holidayName);
            holidayStartDateView = itemView.findViewById(R.id.holidayStart);
        }
    }

    private final LayoutInflater mInflater;
    private List<Place> places;

    public PlaceListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new PlaceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, final int position) {
        if (places != null) {
            Place current = places.get(position);
            holder.holidayNameView.setText(current.getName());

            final SimpleDateFormat f = new SimpleDateFormat("dd MMM YYYY", Locale.UK);
            String date = f.format(current.getDate());
            holder.holidayStartDateView.setText(date);
        } else {
            // Covers the case of data not being ready yet.
            holder.holidayNameView.setText("No Holidays");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, OpenPlace.class);
                intent.putExtra("PLACE_ID", places.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // holidays has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (places != null)
            return places.size();
        else return 0;
    }

}
