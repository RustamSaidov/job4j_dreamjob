package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.CityStore;
import ru.job4j.dreamjob.store.PostDBStore;
import ru.job4j.dreamjob.store.PostStore;

import java.util.Collection;

@ThreadSafe
@Service
public class PostService {
    private final CityStore cityStore;

    private final PostDBStore store;


    public PostService(PostDBStore store, CityStore cityStore) {
        this.store = store;
        this.cityStore = cityStore;
    }

    public Collection<Post> findAll() {
        return store.findAll();
    }

    public void add(Post post) {
        String cityName = cityStore.findById(post.getCity().getId()).getName();
        City city = new City(post.getCity().getId(), cityName);
        post.setCity(city);
        store.add(post);
    }

    public Post findById(Integer id) {
        return store.findById(id);
    }

    public Post update(Post post) {
        post.setCity(cityStore.findById(post.getCity().getId()));
        return store.update(post);
    }
}
