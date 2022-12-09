package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.PostStore;

import java.util.List;

public class PostService {
    private final PostStore store = PostStore.instOf();

    public List<Post> findAll() {
        return (List<Post>) store.findAll();
    }

    public void add(Post post) {
        store.add(post);
    }

    public Post findById(Integer id) {
        return store.findById(id);
    }

    public Post update(Post post) {
        return store.update(post);
    }
}
