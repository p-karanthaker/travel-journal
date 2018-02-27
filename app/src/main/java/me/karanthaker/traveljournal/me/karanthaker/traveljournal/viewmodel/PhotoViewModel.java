package me.karanthaker.traveljournal.me.karanthaker.traveljournal.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Photo;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.repository.PhotoRepository;

/**
 * Created by KTHAKER on 27/02/2018.
 */

public class PhotoViewModel extends AndroidViewModel {

    private PhotoRepository repository;

    private LiveData<List<Photo>> allPhotos;

    public PhotoViewModel(Application application) {
        super(application);
        repository = new PhotoRepository(application);
        allPhotos = repository.getAllPhotos();
    }

    public LiveData<List<Photo>> getAllPhotos() {
        return allPhotos;
    }

    public void insert(Photo photo) {
        repository.insert(photo);
    }

    public void delete(Photo photo) {
        repository.delete(photo);
    }

}
