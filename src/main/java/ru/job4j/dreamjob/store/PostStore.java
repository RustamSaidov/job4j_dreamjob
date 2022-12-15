package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class PostStore {

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private static AtomicInteger id = new AtomicInteger();
    private final CityStore cityStore;

    private PostStore(CityStore cityStore) {
        this.cityStore = cityStore;
        posts.put(1, new Post(1, "Junior Java Job", "Работа для начинающего программиста", LocalDateTime.now(), true, cityStore.findById(1)));
        posts.put(2, new Post(2, "Middle Java Job", "Работа для опытного программиста", LocalDateTime.now(), true, cityStore.findById(1)));
        posts.put(3, new Post(3, "Senior Java Job", "Работа для кунг-фу программиста", LocalDateTime.now(), true, cityStore.findById(1)));
    }

    public int getUniqueId() {
        while (posts.containsKey(id.get())) {
            id.incrementAndGet();
        }
        return id.get();
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public void add(Post post) {
        post.setId(getUniqueId());
        posts.put(post.getId(), post);
    }

    public Post findById(Integer id) {
        return posts.get(id);
    }

    public Post update(Post post) {
        return posts.replace(post.getId(), post);
    }
}