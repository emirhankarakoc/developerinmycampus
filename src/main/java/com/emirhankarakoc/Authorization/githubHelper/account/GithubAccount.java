package com.emirhankarakoc.Authorization.githubHelper.account;

import com.emirhankarakoc.Authorization.githubHelper.repos.GithubRepository;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class GithubAccount {
    @Id
    private String id;

    private String login;
    private String avatarUrl;
    private int public_repos;
    private String bio;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "repositoryId")
    private List<GithubRepository> repositories;
}
