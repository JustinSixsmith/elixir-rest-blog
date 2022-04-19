package com.example.restblog.web;

import com.example.restblog.data.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
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
        return new User(userId, "Bob Smith", "bobsemail@rr.com", "password129", null, User.Role.ADMIN);
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

    @GetMapping("/username")
    private User getByUsername(@RequestParam String username) {
        return new User(989, username, "jodiesmail@myspace.com", "password989", null, User.Role.USER);
    }

    @GetMapping("/email")
    private User getByEmail(@RequestParam String email) {
        return new User(223, "Wendy Bendy", "wendyissmall@emailspace.com", "password9921", null, User.Role.USER);
    }

    @PutMapping("{userId}/updatePassword")
    private void updateUserPassword(@PathVariable Long userId, @RequestParam(required = false) String oldPassword,
        @Valid @Size(min = 3) @RequestParam String newPassword) {
        System.out.println("Ready to change password for user ID: " + userId + " from " + oldPassword + " to " + newPassword);
    }
}