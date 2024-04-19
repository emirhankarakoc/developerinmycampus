package com.emirhankarakoc.Authorization.users;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegisterRequest {
    private String mail;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private LocalDateTime birthDate;
    private UserType userType;
}
