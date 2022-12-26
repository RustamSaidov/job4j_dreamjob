package ru.job4j.dreamjob.store;

import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CandidateDBStoreTest {
    @Test
    public void whenCreatePost() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        byte[] photo = new byte[]{};
        Candidate candidate = new Candidate(1, "Женя Иванов ", "Выпускник Geek brains", LocalDateTime.now(), true, new City(1, "Москва"), photo);
        store.add(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName(), is(candidate.getName()));
    }
}