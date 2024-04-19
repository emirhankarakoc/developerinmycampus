package com.emirhankarakoc.Authorization.users;

import com.emirhankarakoc.Authorization.companies.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByToken(String token);
    Optional<User> findById(String id);
    Optional<User> existsByUsername(String username);

    List<User> findAllByAccountStatus(AccountStatus accountStatus);


}
