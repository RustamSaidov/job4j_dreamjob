package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.PostStore;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class PostController {

    private final PostStore postStore = PostStore.instOf();
    private static AtomicInteger id = new AtomicInteger();

    @GetMapping("/posts")
    public String posts(Model model) {
        model.addAttribute("posts", postStore.findAll());
        return "posts";
    }

    @GetMapping("/formAddPost")
    public String addPost(Model model) {
        model.addAttribute("post", new Post(0, "Заполните название", "Заполните описание", LocalDateTime.now()));
        return "addPost";
    }

    /*Это старый доспринговский вариант:
    @PostMapping("/createPost")
    public String createPost(HttpServletRequest req) {
        String name = req.getParameter("name");
        System.out.println(name);
        postStore.add(new Post(1, name));
        return "redirect:/posts";
    }
     */

    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post) {
        post.setId(id.getAndIncrement());
        postStore.add(post);
        return "redirect:/posts";
    }
}