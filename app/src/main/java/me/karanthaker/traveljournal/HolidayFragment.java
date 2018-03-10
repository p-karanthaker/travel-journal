package me.karanthaker.traveljournal;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import me.karanthaker.traveljournal.me.karanthaker.traveljournal.adapter.HolidayListAdapter;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Holiday;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.viewmodel.HolidayViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class HolidayFragment extends Fragment {

    private RecyclerView recyclerView;
    private HolidayListAdapter adapter;
    private HolidayViewModel holidayViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        holidayViewModel = ViewModelProviders.of(this).get(HolidayViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_items, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerview);

        adapter = new HolidayListAdapter(rootView.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        holidayViewModel.getAllHolidays().observe(this, new Observer<List<Holiday>>() {
            @Override
            public void onChanged(@Nullable List<Holiday> holidays) {
                adapter.setHolidays(holidays);
            }
        });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                if (swipeDir == ItemTouchHelper.LEFT) {
                    holidayViewModel.delete(holidayViewModel.getAllHolidays().getValue().get(viewHolder.getAdapterPosition()));
                    Toast.makeText(rootView.getContext(), "Deleted holiday.", Toast.LENGTH_SHORT).show();
                } else if (swipeDir == ItemTouchHelper.RIGHT) {
                    Intent intent = new Intent(getActivity(), EditHoliday.class);
                    intent.putExtra("HOLIDAY_ID", holidayViewModel.getAllHolidays().getValue().get(viewHolder.getAdapterPosition()).getId());
                    getActivity().startActivity(intent);
                }

                adapter.notifyItemChanged(viewHolder.getAdapterPosition());
            }

            @Override
            public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
                super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return rootView;
    }

}
