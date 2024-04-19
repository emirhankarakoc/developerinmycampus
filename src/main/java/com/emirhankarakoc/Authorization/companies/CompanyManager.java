package com.emirhankarakoc.Authorization.companies;


import com.emirhankarakoc.Authorization.companies.DTOS.CreateCompanyRequest;
import com.emirhankarakoc.Authorization.companies.DTOS.CreateCompanyResponse;
import com.emirhankarakoc.Authorization.users.User;
import com.emirhankarakoc.Authorization.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.emirhankarakoc.Authorization.companies.Company.companiesToDTO;
import static com.emirhankarakoc.Authorization.users.User.userToDTO;

@AllArgsConstructor
@Service
public class CompanyManager implements CompanyService{
    private final CompanyRepository repository;
    private final UserRepository userRepository;


    public List<CompanyDTO> getAllCompanies() {

        return companiesToDTO(repository.findAll());
    }

    public CreateCompanyResponse createCompany(CreateCompanyRequest request, User companyOwner) {
        Company company = new Company();
        company.setId(UUID.randomUUID().toString());
        company.setFullname(request.getFullname());
        company.setCreateddatetime(LocalDateTime.now());
        company.setUpdateddatetime(LocalDateTime.now());
        company.setEmployeeList(new ArrayList<>());
        company.getEmployeeList().add(companyOwner);
        company.setOwnerUser(companyOwner);
        repository.save(company);
        CreateCompanyResponse response = new CreateCompanyResponse();
        response.setId(company.getId());
        response.setFullname(company.getFullname());
        response.setCreateddatetime(company.getCreateddatetime());
        response.setUpdateddatetime(company.getUpdateddatetime());
        response.setEmployeeList(new ArrayList<>());
        response.getEmployeeList().add(userToDTO(companyOwner));
        response.setOwnerUser(userToDTO(companyOwner));
        return response;

    }
}
