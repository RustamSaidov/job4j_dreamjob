package ru.job4j.dreamjob.store;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.City;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PostDBStoreTest {

    @Test
    public void whenCreateUpdateAndFindAllPosts() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        store.truncateTable();
        Post post1 = new Post(1, "Junior Java Job", new City(1, "Москва"), "Работа для начинающего программиста", LocalDateTime.now(), true);
        Post post2 = new Post(2, "Middle Java Job", new City(1, "Москва"), "Работа для начинающего программиста", LocalDateTime.now(), true);
        store.add(post1);
        store.add(post2);
        List<Post> postList = new ArrayList<>();
        postList.add(post1);
        postList.add(post2);
        List<Post> postListFromDb = store.findAll();
        assertThat(postList, is(postListFromDb));
        Post updatedPost2 = new Post(2, "Senior Java Job", new City(1, "Москва"), "Работа для начинающего программиста", LocalDateTime.now(), true);
        store.update(updatedPost2);
        Post updatedPost2InDb = store.findById(updatedPost2.getId());
        assertThat(updatedPost2InDb.getName(), is(updatedPost2.getName()));
    }

    /*
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
        assertThat(postInDb.getName(), is(post.getName()));
    }

    @Test
    public void whenUpdatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(1, "Junior Java Job", new City(1, "Москва"), "Работа для начинающего программиста", LocalDateTime.now(), true);
        store.add(post);
        Post updatedPost = new Post(1, "Middle Java Job", new City(1, "Москва"), "Работа для начинающего программиста", LocalDateTime.now(), true);
        store.update(updatedPost);
        Post updatedPostInDb = store.findById(updatedPost.getId());
        assertThat(updatedPostInDb.getName(), is(updatedPost.getName()));
    }

    @Test
    public void whenFindAllPosts() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        byte[] photo = new byte[]{};
        Post post1 = new Post(1, "Junior Java Job", new City(1, "Москва"), "Работа для начинающего программиста", LocalDateTime.now(), true);
        Post post2 = new Post(2, "Middle Java Job", new City(1, "Москва"), "Работа для начинающего программиста", LocalDateTime.now(), true);
        store.add(post1);
        store.add(post2);
        List<Post> postList = new ArrayList<>();
        postList.add(post1);
        postList.add(post2);
        List<Post> postListFromDb = store.findAll();
        assertThat(postList, is(postListFromDb));
    }
     */
}