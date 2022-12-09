package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CandidateStore {
    private static final CandidateStore INST = new CandidateStore();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private static AtomicInteger id = new AtomicInteger();

    private CandidateStore() {
        candidates.put(1, new Candidate(1, "Женя Иванов ", "Выпускник Geek brains", LocalDateTime.now()));
        candidates.put(2, new Candidate(2, "Миша Петров", "2 года работы в VK.com", LocalDateTime.now()));
        candidates.put(3, new Candidate(3, "Сидор Сидоров", "5 лет опыта работы в разных компаниях", LocalDateTime.now()));
    }

    public static CandidateStore instOf() {
        return INST;
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
