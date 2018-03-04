package me.karanthaker.traveljournal.me.karanthaker.traveljournal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import me.karanthaker.traveljournal.R;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Holiday;

/**
 * Created by KTHAKER on 27/02/2018.
 */

public class HolidayListAdapter extends RecyclerView.Adapter<HolidayListAdapter.HolidayViewHolder> {

    class HolidayViewHolder extends RecyclerView.ViewHolder {
        private final TextView holidayNameView;
        private final TextView holidayStartDateView;
        private final TextView holidayEndDateView;

        private HolidayViewHolder(View itemView) {
            super(itemView);
            holidayNameView = itemView.findViewById(R.id.holidayName);
            holidayStartDateView = itemView.findViewById(R.id.holidayStart);
            holidayEndDateView = itemView.findViewById(R.id.holidayEnd);
        }
    }

    private final LayoutInflater mInflater;
    private List<Holiday> holidays; // Cached copy of words

    public HolidayListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public HolidayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new HolidayViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HolidayViewHolder holder, int position) {
        if (holidays != null) {
            Holiday current = holidays.get(position);
            holder.holidayNameView.setText(current.getName());

            final SimpleDateFormat f = new SimpleDateFormat("dd MMM YYYY", Locale.UK);
            holder.holidayStartDateView.setText(f.format(current.getStartDate()));
            holder.holidayEndDateView.setText(f.format(current.getEndDate()));
        } else {
            // Covers the case of data not being ready yet.
            holder.holidayNameView.setText("No Holidays");
        }
    }

    public void setHolidays(List<Holiday> holidays) {
        this.holidays = holidays;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // holidays has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (holidays != null)
            return holidays.size();
        else return 0;
    }

}
