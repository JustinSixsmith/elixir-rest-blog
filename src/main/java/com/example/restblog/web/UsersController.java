package com.example.restblog.web;

import com.example.restblog.data.User;
import com.example.restblog.data.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/users", headers = "Accept=application/json")
public class UsersController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    private List<User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping("{userId}")
    private Optional<User> getById(@PathVariable Long userId) {
        return userRepository.findById(userId);
    }

    @GetMapping("me")
    private User getMyInfo(OAuth2Authentication auth) {
        // TODO: return the currently logged in user info
        String email = auth.getName();
        return userRepository.findByEmail(email);
    }

    @PostMapping("create")
    private void createUser(@RequestBody User newUser) {
        newUser.setCreatedAt(LocalDate.now());
        newUser.setRole(User.Role.USER);
        String plainTextPassword = newUser.getPassword();
        String encrypedPassword = passwordEncoder.encode(plainTextPassword);
        newUser.setPassword(encrypedPassword);
        userRepository.save(newUser);
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

    @PutMapping("{userId}/updatePassword")
    private void updateUserPassword(@PathVariable Long userId, @RequestParam(required = false) String oldPassword,
        @Valid @Size(min = 3) @RequestParam String newPassword) {
        User userRequestingUpdate = userRepository.getById(userId);
        String plainTextPassword = userRequestingUpdate.getPassword();
        String encrypedPassword = passwordEncoder.encode(plainTextPassword);
        userRequestingUpdate.setPassword(encrypedPassword);
        userRepository.save(userRequestingUpdate);
        System.out.println("Ready to change password for user ID: " + userId + " from " + oldPassword + " to " + newPassword);
    }
}