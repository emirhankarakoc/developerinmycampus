package com.emirhankarakoc.Authorization.githubHelper.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GithubAccountRepository extends JpaRepository<GithubAccount,String> {
}
