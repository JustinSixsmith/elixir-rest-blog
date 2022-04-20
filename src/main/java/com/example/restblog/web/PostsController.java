package com.example.restblog.web;

import com.example.restblog.data.Post;
import com.example.restblog.data.PostsRepository;
import com.example.restblog.data.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/posts", headers = "Accept=application/json")
public class PostsController {

    private PostsRepository postsRepository;

    public PostsController(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
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
        return postsRepository.findAll();
    }

    @GetMapping("{postId}")
    private Post getById(@PathVariable Long postId) {
        Post post = postsRepository.getById(postId);
        return post;
    }

    @PostMapping
    private void createPost(@RequestBody Post newPost) {
        Post postToAdd = new Post(newPost.getTitle(), newPost.getContent());
        postsRepository.save(postToAdd);
        System.out.println("Post created");
    }

    @PutMapping("{postId}")
    private void updatePost(@PathVariable Long postId, @RequestBody Post updatedPost) {
        Post postToUpdate = postsRepository.getById(postId);
        postToUpdate.setContent(updatedPost.getContent());
        postToUpdate.setTitle(updatedPost.getTitle());
        postsRepository.save(postToUpdate);
        System.out.println("Updating post number: " + postId + " with: " + updatedPost);
    }

    @DeleteMapping("{postId}")
    private void deletePost(@PathVariable Long postId) {
        Post postToDelete = postsRepository.getById(postId);
        postsRepository.delete(postToDelete);
        System.out.println("Deleting post with ID: " + postId);
    }
}
