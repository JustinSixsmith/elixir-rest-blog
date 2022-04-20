package com.example.restblog.web;

import com.example.restblog.data.Post;
import com.example.restblog.data.PostsRepository;
import com.example.restblog.data.User;
import com.example.restblog.data.UserRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/users", headers = "Accept=application/json")
public class UsersController {

    private UserRepository userRepository;

    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    private static final Post POST1 = new Post(1L, "Post 1", "Blah", null);
//    private static final Post POST2 = new Post(2L, "Post 2", "Blah blah", null);
//    private static final Post POST3 = new Post(3L, "Post 3", "Blah blah blah", null);
//    private static final Post POST4 = new Post(4L, "Post 4", "Blah blah blah blah", null);
//    private static final Post POST5 = new Post(5L, "Post 5", "Blah blah blah blah blah", null);
//    private static final Post POST6 = new Post(6L, "Post 6", "Blah blah blah blah blah blah", null);
//    private static final Post POST7 = new Post(7L, "Post 7", "Blah blah blah blah blah blah", null);
//    private static final Post POST8 = new Post(8L, "Post 8", "Blah blah blah blah blah blah", null);
//    private static final Post POST9 = new Post(9L, "Post 9", "Blah blah blah blah blah blah", null);

    @GetMapping
    private List<User> getAll() {
//        ArrayList<User> users = new ArrayList<>();
//        users.add(new User( 1L, "User 1", "email 1", "1111", null, User.Role.ADMIN, Arrays.asList(POST1, POST2)));
//        users.add(new User(2L, "User 2", "email 2", "2222", null, User.Role.ADMIN, Arrays.asList(POST3, POST4)));
//        users.add(new User(3L, "User 3", "email 3", "3333", null, User.Role.ADMIN, Arrays.asList(POST5, POST6)));
        return userRepository.findAll();
    }

    @GetMapping("{userId}")
    private User getById(@PathVariable Long userId) {
//        return new User(userId, "Bob Smith", "bobsemail@rr.com", "password129", null, User.Role.ADMIN, Arrays.asList(POST7, POST8));
        return userRepository.getById(userId);
    }

    @PostMapping
    private void createUser(@RequestBody User newUser) {
        User user = new User(newUser.getUsername(), newUser.getEmail(), newUser.getPassword(), newUser.getCreatedAt(), newUser.getRole());
        userRepository.save(user);
        System.out.println("User created");
    }

    @PutMapping("{userId}")
    private void updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {
        User userToUpdate = userRepository.getById(userId);
        userToUpdate.setUsername(updatedUser.getUsername());
        userToUpdate.setEmail(updatedUser.getEmail());
        userToUpdate.setPassword(updatedUser.getPassword());
        userToUpdate.setCreatedAt(updatedUser.getCreatedAt());
        userToUpdate.setRole(updatedUser.getRole());
        userRepository.save(userToUpdate);
        System.out.println("Updating user number: " + userId + " with: " + updatedUser);
    }

    @DeleteMapping("{userId}")
    private void deleteUser(@PathVariable Long userId) {
        User userToDelete = userRepository.getById(userId);
        userRepository.delete(userToDelete);
        System.out.println("Deleting user with ID: " + userId);
    }

//    @GetMapping("/username")
//    private User getByUsername(@RequestParam String username) {
//        return new User(989, username, "jodiesmail@myspace.com", "password989", null, User.Role.USER, List.of(POST8));
//    }

//    @GetMapping("/email")
//    private User getByEmail(@RequestParam String email) {
//        return new User(223, "Wendy Mathis", email, "password9921", null, User.Role.USER, List.of(POST9));
//    }

    @PutMapping("{userId}/updatePassword")
    private void updateUserPassword(@PathVariable Long userId, @RequestParam(required = false) String oldPassword,
        @Valid @Size(min = 3) @RequestParam String newPassword) {
        User userRequestingUpdate = userRepository.getById(userId);
        userRequestingUpdate.setPassword(newPassword);
        userRepository.save(userRequestingUpdate);
        System.out.println("Ready to change password for user ID: " + userId + " from " + oldPassword + " to " + newPassword);
    }
}