package com.emirhankarakoc.Authorization.companies;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company,String> {
    Optional<Company> findById(String id);
    List<Company> findAll();
}

