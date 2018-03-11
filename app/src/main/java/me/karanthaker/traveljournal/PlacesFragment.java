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

import me.karanthaker.traveljournal.me.karanthaker.traveljournal.adapter.PlaceListAdapter;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Place;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.viewmodel.PlaceViewModel;

/**
 * Created by Karan on 10/03/2018.
 */

public class PlacesFragment extends Fragment {


    private RecyclerView recyclerView;
    private PlaceListAdapter adapter;
    private PlaceViewModel placeViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placeViewModel = ViewModelProviders.of(this).get(PlaceViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_items, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerview);

        adapter = new PlaceListAdapter(rootView.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        placeViewModel.getAllPlaces().observe(this, new Observer<List<Place>>() {
            @Override
            public void onChanged(@Nullable List<Place> places) {
                adapter.setPlaces(places);
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
                    placeViewModel.delete(placeViewModel.getAllPlaces().getValue().get(viewHolder.getAdapterPosition()));
                    Toast.makeText(rootView.getContext(), "Deleted holiday.", Toast.LENGTH_SHORT).show();
                } else if (swipeDir == ItemTouchHelper.RIGHT) {
                    Intent intent = new Intent(getActivity(), AddPlace.class);
                    intent.putExtra("PLACE_ID", placeViewModel.getAllPlaces().getValue().get(viewHolder.getAdapterPosition()).getId());
                    intent.putExtra("NEW_ITEM", false);
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
