package me.karanthaker.traveljournal.me.karanthaker.traveljournal.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import me.karanthaker.traveljournal.OpenHoliday;
import me.karanthaker.traveljournal.R;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Holiday;

/**
 * Created by KTHAKER on 27/02/2018.
 */

public class HolidayListAdapter extends RecyclerView.Adapter<HolidayListAdapter.HolidayViewHolder> {

    private final LayoutInflater mInflater;
    private List<Holiday> holidays;
    public HolidayListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public HolidayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new HolidayViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HolidayViewHolder holder, final int position) {
        if (holidays != null) {
            Holiday current = holidays.get(position);
            holder.holidayNameView.setText(current.getName());

            final SimpleDateFormat f = new SimpleDateFormat("dd MMM YYYY", Locale.UK);
            String startDate = f.format(current.getStartDate());
            String endDate = f.format(current.getEndDate());
            String date = String.format("%s - %s", startDate, endDate);
            holder.holidayStartDateView.setText(date);
        } else {
            // Covers the case of data not being ready yet.
            holder.holidayNameView.setText("No Holidays");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, OpenHoliday.class);
                intent.putExtra("HOLIDAY_ID", holidays.get(position).getId());
                context.startActivity(intent);
            }
        });

        Button share = holder.itemView.findViewById(R.id.shareItem);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Holiday toShare = holidays.get(position);
                Context context = view.getContext();
                Intent shareItem = new Intent();
                shareItem.setAction(Intent.ACTION_SEND);

                final SimpleDateFormat f = new SimpleDateFormat("dd MMM YYYY", Locale.UK);
                String startDate = f.format(toShare.getStartDate());
                String endDate = f.format(toShare.getEndDate());
                String date = String.format("%s - %s", startDate, endDate);

                String message = new StringBuilder("Hey! I went on holiday to ").append(toShare.getName()).append(" on: \n").append(date)
                        .append("\nCheck it out! https://travel-journal.com/holiday/").append(toShare.getId())
                        .append("\nNow I'm storing it in my Travel Journal app! You should download it too so you can share your adventures!").toString();

                shareItem.putExtra(Intent.EXTRA_TEXT, message);
                shareItem.setType("text/plain");
                context.startActivity(shareItem);
            }
        });
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

    class HolidayViewHolder extends RecyclerView.ViewHolder {
        private final TextView holidayNameView;
        private final TextView holidayStartDateView;

        private HolidayViewHolder(View itemView) {
            super(itemView);
            holidayNameView = itemView.findViewById(R.id.holidayName);
            holidayStartDateView = itemView.findViewById(R.id.holidayStart);
        }
    }

}
