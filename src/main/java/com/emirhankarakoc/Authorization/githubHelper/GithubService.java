package com.emirhankarakoc.Authorization.githubHelper;

import com.emirhankarakoc.Authorization.githubHelper.account.GithubAccount;
import com.emirhankarakoc.Authorization.users.User;

public interface GithubService {
    void initUser(String username, User user);

    void fetchRepositories(GithubAccount account);
}
