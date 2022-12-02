package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CandidateStore {
    private static final CandidateStore INST = new CandidateStore();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private CandidateStore() {
        candidates.put(1, new Candidate(1, "Женя Иванов ", "Выпускник Geek brains", LocalDateTime.now()));
        candidates.put(2, new Candidate(2, "Миша Петров", "2 года работы в VK.com", LocalDateTime.now()));
        candidates.put(3, new Candidate(3, "Сидор Сидоров", "5 лет опыта работы в разных компаниях", LocalDateTime.now()));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
