package ru.job4j.dreamjob.store;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.annotations.BeforeTest;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CandidateDBStoreTest {

    @BeforeEach
    @BeforeTest
    @AfterEach
    public void truncateTable() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        store.TruncateTable();
    }

    @Test
    public void whenCreateCandidate() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        byte[] photo = new byte[]{};
        Candidate candidate = new Candidate(1, "Женя Иванов ", "Выпускник Geek brains", LocalDateTime.now(), true, new City(1, "Москва"), photo);
        store.add(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getId(), is(candidate.getId()));
        assertThat(candidateInDb.getName(), is(candidate.getName()));
    }

    @Test
    public void whenUpdateCandidate() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        byte[] photo = new byte[]{};
        Candidate candidate = new Candidate(1, "Женя Иванов ", "Выпускник Geek brains", LocalDateTime.now(), true, new City(1, "Москва"), photo);
        store.add(candidate);
        store.findAll().stream().forEach(System.out::println);
        Candidate updatedCandidate = new Candidate(1, "Петр Петров ", "Выпускник Geek brains", LocalDateTime.now(), true, new City(1, "Москва"), photo);
        store.update(updatedCandidate);
        Candidate updatedCandidateInDb = store.findById(candidate.getId());
        store.findAll().stream().forEach(System.out::println);
        assertThat(updatedCandidateInDb.getId(), is(updatedCandidate.getId()));
        assertThat(updatedCandidateInDb.getName(), is(updatedCandidate.getName()));
    }

    @Test
    public void whenFindAllCandidates(){
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        byte[] photo = new byte[]{};
        Candidate candidate1 = new Candidate(1, "Женя Иванов ", "Выпускник Geek brains", LocalDateTime.now(), true, new City(1, "Москва"), photo);
        Candidate candidate2 = new Candidate(2, "Петр Петров ", "Выпускник Geek brains", LocalDateTime.now(), true, new City(1, "Москва"), photo);
        store.add(candidate1);
        store.add(candidate2);
        List<Candidate> candidateList = new ArrayList<>();
        candidateList.add(candidate1);
        candidateList.add(candidate2);
        List<Candidate> candidateListFromDb = store.findAll();
        assertThat(candidateList, is(candidateListFromDb));
    }
}