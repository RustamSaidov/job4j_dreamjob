package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.store.CandidateStore;
import ru.job4j.dreamjob.store.CityStore;

import java.util.Collection;

@ThreadSafe
@Service
public class CandidateService {

    private final CandidateStore store;
    private final CityStore cityStore;

    public CandidateService(CandidateStore store, CityStore cityStore) {
        this.store = store;
        this.cityStore = cityStore;
    }

    public Collection<Candidate> findAll() {
        return store.findAll();
    }

    public void add(Candidate candidate) {
        String cityName = cityStore.findById(candidate.getCity().getId()).getName();
        City city = new City(candidate.getCity().getId(), cityName);
        candidate.setCity(city);
        store.add(candidate);
    }

    public Candidate findById(Integer id) {
        return store.findById(id);
    }

    public Candidate update(Candidate candidate) {
        return store.update(candidate);
    }
}
