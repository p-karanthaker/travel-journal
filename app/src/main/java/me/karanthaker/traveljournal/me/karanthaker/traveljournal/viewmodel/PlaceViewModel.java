package me.karanthaker.traveljournal.me.karanthaker.traveljournal.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Place;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.repository.PlaceRepository;

/**
 * Created by Karan on 27/02/2018.
 */

public class PlaceViewModel extends AndroidViewModel {

    private PlaceRepository repository;

    private LiveData<List<Place>> allPlaces;

    public PlaceViewModel(Application application) {
        super(application);
        repository = new PlaceRepository(application);
        allPlaces = repository.getAllPlaces();
    }

    public LiveData<List<Place>> getAllPlaces() {
        return allPlaces;
    }

    public Place getPlaceById(int placeId) {
        return repository.getPlaceById(placeId);
    }

    public void update(Place place) {
        repository.update(place);
    }

    public void insert(Place place) {
        repository.insert(place);
    }

    public void delete(Place place) {
        repository.delete(place);
    }

}
