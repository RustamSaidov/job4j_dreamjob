package ru.job4j.dreamjob.store;

import org.junit.Test;
/*import org.junit.jupiter.api.BeforeEach;*/
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CandidateDBStoreTest {

    @Test
    public void whenCreateUpdateAndFindAllCandidates(){
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        store.truncateTable();
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
        store.findAll().stream().forEach(System.out::println);
        Candidate updatedCandidate2 = new Candidate(2, "Петр Петрович Петров ", "Выпускник Geek brains", LocalDateTime.now(), true, new City(1, "Москва"), photo);
        store.update(updatedCandidate2);
        Candidate updatedCandidate2InDb = store.findById(updatedCandidate2.getId());
        store.findAll().stream().forEach(System.out::println);
        assertThat(updatedCandidate2InDb.getName(), is(updatedCandidate2.getName()));
    }
/*

    @BeforeEach
    public void truncateTable() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        store.truncateTable();
    }

    @Test
    public void whenCreateCandidate() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        store.truncateTable();
        byte[] photo = new byte[]{};
        Candidate candidate = new Candidate(1, "Женя Иванов ", "Выпускник Geek brains", LocalDateTime.now(), true, new City(1, "Москва"), photo);
        store.add(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName(), is(candidate.getName()));
    }

    @Test
    public void whenUpdateCandidate() throws InterruptedException {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        System.out.println("!!!");
        store.truncateTable();
        System.out.println("!!!");
        Thread.sleep(1000);
        byte[] photo = new byte[]{};
        Candidate candidate = new Candidate(1, "Женя Иванов ", "Выпускник Geek brains", LocalDateTime.now(), true, new City(1, "Москва"), photo);
        store.add(candidate);
        store.findAll().stream().forEach(System.out::println);
        Candidate updatedCandidate = new Candidate(1, "Петр Петров ", "Выпускник Geek brains", LocalDateTime.now(), true, new City(1, "Москва"), photo);
        store.update(updatedCandidate);
        Candidate updatedCandidateInDb = store.findById(candidate.getId());
        store.findAll().stream().forEach(System.out::println);
        assertThat(updatedCandidateInDb.getName(), is(updatedCandidate.getName()));
        store.truncateTable();
    }

    @Test
    public void whenFindAllCandidates(){
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        store.findAll().stream().forEach(System.out::println);
        System.out.println("!!!");
        store.truncateTable();
        System.out.println("!!!");
        store.findAll().stream().forEach(System.out::println);
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
        store.findAll().stream().forEach(System.out::println);
    }
 */
}