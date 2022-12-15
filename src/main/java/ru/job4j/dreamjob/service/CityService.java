package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.store.CityStore;

import java.util.Collection;

@ThreadSafe
@Service
public class CityService {

    private final CityStore store;

    public CityService(CityStore store) {
        this.store = store;
    }

    public Collection<City> getAllCities() {
        return store.findAll();
    }

    public City findById(int id) {
        return store.findById(id);
    }

    public void add(City city) {
        store.add(city);
    }

    public City update(City city) {
        return store.update(city);
    }

}