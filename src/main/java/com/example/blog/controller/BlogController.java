package com.example.blog.controller;

import com.example.blog.models.Post;
import com.example.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog-main")
    public String blog(Model model){
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
    }
    @GetMapping("/blog-main/add")
    public String blogAdd(Model model){
        return "blog-add";
    }
    @PostMapping("/blog-main/add")
    public String blogPostAdd(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model){
//        Post post = new Post(title, anons, full_text);
        Post rollingStones = Post.builder()     //@Builder annotations ishlatildi!(Lombok library sidan)
                .title(title)
                .anons(anons)
                .full_text(full_text)
                .build();
        postRepository.save(rollingStones);
        return "redirect:/blog-main";
    }
    @GetMapping("/blog-main/{id}")
    public String blogAdd(@PathVariable(value = "id") long id, Model model){
        if (!postRepository.existsById(id)){
            return "redire" +
                    "ct:/blog-main";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> result = new ArrayList<>();
        post.ifPresent(result::add);
        model.addAttribute("post", result);
        return "blog-details";
    }
    @GetMapping("/blog-main/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model){
        if (!postRepository.existsById(id)){
            return "redirect:/blog-main";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> result = new ArrayList<>();
        post.ifPresent(result::add);
        model.addAttribute("post", result);
        return "blog-edit";
    }
    @PostMapping("/blog-main/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model){
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);
        return "redirect:/blog-main";
    }
    @PostMapping("/blog-main/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") long id, Model model){
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/blog-main";
    }

}
