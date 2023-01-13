//package ru.job4j.dreamjob.store;
//
//import net.jcip.annotations.ThreadSafe;
//import org.springframework.stereotype.Repository;
//import ru.job4j.dreamjob.model.City;
//
//import java.util.Collection;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.atomic.AtomicInteger;
//
//@ThreadSafe
//@Repository
//public class CityStore {
//    private Map<Integer, City> cities = new ConcurrentHashMap<>();
//    private static AtomicInteger id = new AtomicInteger();
//
//    private CityStore() {
//        cities.put(1, new City(1, "Москва"));
//        cities.put(2, new City(2, "СПб"));
//        cities.put(3, new City(3, "Екб"));
//    }
//
//    public int getUniqueId() {
//        while (cities.containsKey(id.get())) {
//            id.incrementAndGet();
//        }
//        return id.get();
//    }
//
//    public Collection<City> findAll() {
//        return cities.values();
//    }
//
//    public void add(City city) {
//        city.setId(getUniqueId());
//        cities.put(city.getId(), city);
//    }
//
//    public City findById(Integer id) {
//        return cities.get(id);
//    }
//
//    public City update(City city) {
//        return cities.replace(city.getId(), city);
//    }
//}
