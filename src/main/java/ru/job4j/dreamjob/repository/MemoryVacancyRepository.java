package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Vacancy;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
public class MemoryVacancyRepository implements VacancyRepository {

    private int nextId = 1;

    private final Map<Integer, Vacancy> vacancies = new HashMap<>();

    public MemoryVacancyRepository() {
        save(new Vacancy(0, "Intern Java Developer", "Great job for cash1", LocalDateTime.now(), true));
        save(new Vacancy(0, "Junior Java Developer", "Great job for cash2", LocalDateTime.now(), true));
        save(new Vacancy(0, "Junior+ Java Developer", "Great job for cash3", LocalDateTime.now(), true));
        save(new Vacancy(0, "Middle Java Developer", "Great job for cash4", LocalDateTime.now(), false));
        save(new Vacancy(0, "Middle+ Java Developer", "Great job for cash5", LocalDateTime.now(), false));
        save(new Vacancy(0, "Senior Java Developer", "Great job for cash6", LocalDateTime.now(), false));
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(nextId++);
        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }

    @Override
    public boolean deleteById(int id) {
        return vacancies.remove(id) != null;
    }

    @Override
    public boolean update(Vacancy vacancy) {
        return vacancies.computeIfPresent(vacancy.getId(), (id, oldVacancy) -> {
            return new Vacancy(oldVacancy.getId(), vacancy.getTitle(), vacancy.getDescription(), vacancy.getCreationDate(), vacancy.getVisible());
        }) != null;
    }



    @Override
    public Optional<Vacancy> findById(int id) {
        return Optional.ofNullable(vacancies.get(id));
    }

    @Override
    public Collection<Vacancy> findAll() {
        return vacancies.values();
    }

}