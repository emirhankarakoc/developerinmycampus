package com.emirhankarakoc.Authorization.users;

import lombok.Data;

@Data
public class AddGithubUsernameRequest {
    String token;
    String username;
}

