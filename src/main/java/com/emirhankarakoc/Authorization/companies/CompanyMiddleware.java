package com.emirhankarakoc.Authorization.companies;


import com.emirhankarakoc.Authorization.companies.DTOS.CreateCompanyRequest;
import com.emirhankarakoc.Authorization.companies.DTOS.CreateCompanyResponse;
import com.emirhankarakoc.Authorization.exceptions.general.NotfoundException;
import com.emirhankarakoc.Authorization.jwt.JWTService;
import com.emirhankarakoc.Authorization.users.User;
import com.emirhankarakoc.Authorization.users.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CompanyMiddleware {
    private final CompanyService service;
    private final CompanyRepository repository;
    private final UserRepository userRepository;
    private final JWTService jwtService;

    public List<CompanyDTO> getAllCompanies() {
        return service.getAllCompanies();
    }

    public CreateCompanyResponse createCompany(CreateCompanyRequest r) {
        jwtService.validateToken(r.getToken());
        User companyOwner = userRepository.findByToken(r.getToken()).orElseThrow(()-> new NotfoundException("Invalid token."));

        return service.createCompany(r,companyOwner);
    }


}
