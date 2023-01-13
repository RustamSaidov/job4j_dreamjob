//package ru.job4j.dreamjob.store;
//
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import ru.job4j.dreamjob.Main;
//import ru.job4j.dreamjob.model.Candidate;
//import ru.job4j.dreamjob.model.City;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.*;
//
//public class CandidateDBStoreTest {
//
//    @BeforeEach
//    public void truncateTable() {
//        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
//        store.truncateTable();
//    }
//
//    @Test
//    public void whenCreateCandidate() {
//        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
//        byte[] photo = new byte[]{};
//        Candidate candidate = new Candidate(1, "Женя Иванов ", "Выпускник Geek brains", LocalDateTime.now(), true, new City(1, "Москва"), photo);
//        store.add(candidate);
//        Candidate candidateInDb = store.findById(candidate.getId());
//        assertThat(candidateInDb.getName()).isEqualTo(candidate.getName());
//    }
//
//    @Test
//    public void whenUpdateCandidate() throws InterruptedException {
//        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
//        byte[] photo = new byte[]{};
//        Candidate candidate = new Candidate(1, "Женя Иванов ", "Выпускник Geek brains", LocalDateTime.now(), true, new City(1, "Москва"), photo);
//        store.add(candidate);
//        Candidate updatedCandidate = new Candidate(candidate.getId(), "Петр Петров ", "Выпускник Geek brains", LocalDateTime.now(), true, new City(1, "Москва"), photo);
//        store.update(updatedCandidate);
//        Candidate updatedCandidateInDb = store.findById(candidate.getId());
//        assertThat(updatedCandidateInDb.getName()).isEqualTo(updatedCandidate.getName());
//    }
//
//    @Test
//    public void whenFindAllCandidates() {
//        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
//        byte[] photo = new byte[]{};
//        Candidate candidate1 = new Candidate(1, "Женя Иванов ", "Выпускник Geek brains", LocalDateTime.now(), true, new City(1, "Москва"), photo);
//        Candidate candidate2 = new Candidate(2, "Петр Петров ", "Выпускник Geek brains", LocalDateTime.now(), true, new City(1, "Москва"), photo);
//        store.add(candidate1);
//        store.add(candidate2);
//        List<Candidate> candidateList = new ArrayList<>();
//        candidateList.add(candidate1);
//        candidateList.add(candidate2);
//        List<Candidate> candidateListFromDb = store.findAll();
//        assertThat(candidateList).isEqualTo(candidateListFromDb);
//    }
//}