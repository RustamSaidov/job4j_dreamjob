package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.CityStore;
import ru.job4j.dreamjob.store.PostDBStore;

import java.util.List;

@ThreadSafe
@Service
public class PostService {
    private final PostDBStore store;
    private final CityService cityService;


    public PostService(PostDBStore store, CityService cityService) {
        this.store = store;
        this.cityService = cityService;
    }

    public List<Post> findAll() {
        List<Post> posts = store.findAll();
        posts.forEach(
                post -> post.setCity(
                        cityService.findById(post.getCity().getId())
                )
        );
        return posts;
    }

    public void add(Post post) {
        String cityName = cityService.findById(post.getCity().getId()).getName();
        City city = new City(post.getCity().getId(), cityName);
        post.setCity(city);
        store.add(post);
    }

    public Post findById(Integer id) {
        return store.findById(id);
    }

    public void update(Post post) {
        store.update(post);
    }
}

