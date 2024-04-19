package com.emirhankarakoc.Authorization.companies;


import com.emirhankarakoc.Authorization.companies.DTOS.CreateCompanyRequest;
import com.emirhankarakoc.Authorization.companies.DTOS.CreateCompanyResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/companies")
@AllArgsConstructor
public class CompanyController {
    private final CompanyMiddleware middleware;

    @PostMapping
    public CreateCompanyResponse postCompany(CreateCompanyRequest r){
        return middleware.createCompany(r);
    }
    @GetMapping
    public List<CompanyDTO> getAll(){
        return middleware.getAllCompanies();
    }



}
