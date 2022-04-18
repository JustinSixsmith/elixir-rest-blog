package com.example.restblog.web;

import com.example.restblog.data.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/users", headers = "Accept=application/json")
public class UsersController {

    @GetMapping
    private List<User> getAll() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User( 1L, "User 1", "email 1", "1111", null, User.Role.ADMIN));
        users.add(new User(2L, "User 2", "email 2", "2222", null, User.Role.ADMIN));
        users.add(new User(3L, "User 3", "email 3", "3333", null, User.Role.ADMIN));
        return users;
    }

    @GetMapping("{userId}")
    private User getById(@PathVariable Long userId) {
        return new User(userId, "Bob Smith", "bobsemail", "password1", null, User.Role.ADMIN);
    }

    @PostMapping
    private void createUser(@RequestBody User newUser) {
        System.out.println("Ready to add user: " + newUser);
    }

    @PutMapping("{userId}")
    private void updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {
        System.out.println("Updating user number: " + userId + " with: " + updatedUser);
    }

    @DeleteMapping("{userId}")
    private void deleteUser(@PathVariable Long userId) {
        System.out.println("Deleting user with ID: " + userId);
    }
}