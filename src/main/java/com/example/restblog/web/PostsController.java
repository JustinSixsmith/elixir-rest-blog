package com.example.restblog.web;

import com.example.restblog.data.*;
import com.example.restblog.services.EmailService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/posts", headers = "Accept=application/json")
public class PostsController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EmailService emailService;

    public PostsController(PostRepository postsRepository, UserRepository userRepository, CategoryRepository categoryRepository, EmailService emailService) {
        this.postRepository = postsRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.emailService = emailService;
    }

    @GetMapping
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    @GetMapping("{postId}")
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    public Optional<Post> getById(@PathVariable Long postId) {
        return postRepository.findById(postId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    public void createPost(@RequestBody Post newPost, OAuth2Authentication auth) {
//        newPost.setAuthor(userRepository.getById(1L));

        String email = auth.getName(); // yes, the email is found under "getName()"
        User author = userRepository.findByEmail(email); // .get(); // use the email to get the user who made the request
        newPost.setAuthor(author); //set the user to the post

        List<Category> categories = new ArrayList<>();
        categories.add(categoryRepository.findCategoryById(1L));
        categories.add(categoryRepository.findCategoryById(2L));
        newPost.setCategories(categories);
        postRepository.save(newPost);
        emailService.prepareAndSend(newPost, newPost.getTitle(), newPost.getContent());
        System.out.println("Post created");
    }

    @PutMapping("{postId}")
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    public void updatePost(@PathVariable Long postId, @RequestBody Post updatedPost) {
        Post postToUpdate = postRepository.getById(postId);
        postToUpdate.setContent(updatedPost.getContent());
        postToUpdate.setTitle(updatedPost.getTitle());
        postRepository.save(postToUpdate);
        System.out.println("Updating post number: " + postId + " with: " + updatedPost);
    }

    @DeleteMapping("{postId}")
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    public void deletePost(@PathVariable Long postId) {
        Post postToDelete = postRepository.getById(postId);
        postRepository.delete(postToDelete);
        System.out.println("Deleting post with ID: " + postId);
    }

}
