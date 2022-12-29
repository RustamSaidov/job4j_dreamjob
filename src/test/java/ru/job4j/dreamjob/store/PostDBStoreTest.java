package ru.job4j.dreamjob.store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.City;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class PostDBStoreTest {

    @BeforeEach
    public void truncateTable() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        store.truncateTable();
    }

    @Test
    public void whenCreatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(1, "Junior Java Job", new City(1, "Москва"), "Работа для начинающего программиста", LocalDateTime.now(), true);
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName()).isEqualTo(post.getName());
    }

    @Test
    public void whenUpdatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(1, "Junior Java Job", new City(1, "Москва"), "Работа для начинающего программиста", LocalDateTime.now(), true);
        store.add(post);
        Post updatedPost2 = new Post(post.getId(), "Middle Java Job", new City(1, "Москва"), "Работа для начинающего программиста", LocalDateTime.now(), true);
        store.update(updatedPost2);
        Post updatedPost2InDb = store.findById(updatedPost2.getId());
        assertThat(updatedPost2InDb.getName()).isEqualTo(updatedPost2.getName());
    }

    @Test
    public void whenFindAllPosts() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        store.truncateTable();
        byte[] photo = new byte[]{};
        Post post1 = new Post(1, "Junior Java Job", new City(1, "Москва"), "Работа для начинающего программиста", LocalDateTime.now(), true);
        Post post2 = new Post(2, "Middle Java Job", new City(1, "Москва"), "Работа для начинающего программиста", LocalDateTime.now(), true);
        store.add(post1);
        store.add(post2);
        List<Post> postList = new ArrayList<>();
        postList.add(post1);
        postList.add(post2);
        List<Post> postListFromDb = store.findAll();
        assertThat(postList).isEqualTo(postListFromDb);
    }
}