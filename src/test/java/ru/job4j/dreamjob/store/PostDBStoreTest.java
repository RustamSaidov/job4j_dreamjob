package ru.job4j.dreamjob.store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PostDBStoreTest {

    @BeforeEach
    public void truncateTable() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        store.TruncateTable();
    }


    @Test
    public void whenCreatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(1, "Junior Java Job", new City(1, "Москва"), "Работа для начинающего программиста", LocalDateTime.now(), true);
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getId(), is(post.getId()));
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
        assertThat(updatedPostInDb.getId(), is(updatedPost.getId()));
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
}