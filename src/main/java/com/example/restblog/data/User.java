package com.example.restblog.data;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class User {
    public enum Role {USER, ADMIN}

    private long id;
    private String username;
    private String email;
    private String password;
    private LocalDate createdAt;
    private Role role;

}
