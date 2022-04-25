package com.example.restblog.web;

import com.example.restblog.data.*;
import com.example.restblog.services.EmailService;
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


//    private static final Post POST1 = new Post(1L, "Post 1", "Blah", null);
//    private static final Post POST2 = new Post(2L, "Post 2", "Blah blah", null);
//    private static final Post POST3 = new Post(3L, "Post 3", "Blah blah blah", null);

//    private static final User USER1 = new User(1L, "User 1", "user1@bob.com", "1111", null, User.Role.USER, null);
//    private static final User USER2 = new User(2L, "User 2", "user2@bob.com", "2222", null, User.Role.USER, null);
//    private static final User USER3 = new User(3L, "User 3", "user3@bob.com", "3333", null, User.Role.USER, null);

    @GetMapping
    private List<Post> getAll() {
//        ArrayList<Post> posts = new ArrayList<>();
//        posts.add(new Post(1L, "Post 1", "Blah blah blah", USER1));
//        posts.add(new Post(2L, "Post 2", "Blah blah blah blah", USER2));
//        posts.add(new Post(3L, "Post 3", "Blah blah blah blah blah", USER1));
        return postRepository.findAll();
    }

    @GetMapping("{postId}")
    private Optional<Post> getById(@PathVariable Long postId) {
        return postRepository.findById(postId);
    }

    @PostMapping
    private void createPost(@RequestBody Post newPost, OAuth2Authentication auth) {
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
    private void updatePost(@PathVariable Long postId, @RequestBody Post updatedPost) {
        Post postToUpdate = postRepository.getById(postId);
        postToUpdate.setContent(updatedPost.getContent());
        postToUpdate.setTitle(updatedPost.getTitle());
        postRepository.save(postToUpdate);
        System.out.println("Updating post number: " + postId + " with: " + updatedPost);
    }

    @DeleteMapping("{postId}")
    private void deletePost(@PathVariable Long postId) {
        Post postToDelete = postRepository.getById(postId);
        postRepository.delete(postToDelete);
        System.out.println("Deleting post with ID: " + postId);
    }

}
