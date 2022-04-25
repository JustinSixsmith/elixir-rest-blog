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