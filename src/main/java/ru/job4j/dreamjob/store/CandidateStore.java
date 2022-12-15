package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class CandidateStore {

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private static AtomicInteger id = new AtomicInteger();
    private final CityStore cityStore;

    private CandidateStore(CityStore cityStore) {
        this.cityStore = cityStore;
        candidates.put(1, new Candidate(1, "Женя Иванов ", "Выпускник Geek brains", LocalDateTime.now(), true, this.cityStore.findById(1)));
        candidates.put(2, new Candidate(2, "Миша Петров", "2 года работы в VK.com", LocalDateTime.now(), true, this.cityStore.findById(1)));
        candidates.put(3, new Candidate(3, "Сидор Сидоров", "5 лет опыта работы в разных компаниях", LocalDateTime.now(), true, this.cityStore.findById(1)));
    }

    public int getUniqueId() {
        while (candidates.containsKey(id.get())) {
            id.incrementAndGet();
        }
        return id.get();
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public void add(Candidate candidate) {
        candidate.setId(getUniqueId());
        candidates.put(candidate.getId(), candidate);
    }

    public Candidate findById(Integer id) {
        return candidates.get(id);
    }

    public Candidate update(Candidate candidate) {
        return candidates.replace(candidate.getId(), candidate);
    }
}
