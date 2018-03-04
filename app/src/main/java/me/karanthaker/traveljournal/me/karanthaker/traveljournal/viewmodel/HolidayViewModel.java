package me.karanthaker.traveljournal.me.karanthaker.traveljournal.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Holiday;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.repository.HolidayRepository;

/**
 * Created by Karan on 27/02/2018.
 */

public class HolidayViewModel extends AndroidViewModel {

    private HolidayRepository repository;

    private LiveData<List<Holiday>> allHolidays;

    public HolidayViewModel(Application application) {
        super(application);
        repository = new HolidayRepository(application);
        allHolidays = repository.getAllHolidays();
    }

    public LiveData<List<Holiday>> getAllHolidays() {
        return allHolidays;
    }

    public Holiday getHolidayById(int holidayId) {
        return repository.getHolidayById(holidayId);
    }

    public void update(Holiday holiday) {
        repository.update(holiday);
    }

    public void insert(Holiday holiday) {
        repository.insert(holiday);
    }

    public void delete(Holiday holiday) {
        repository.delete(holiday);
    }

}
