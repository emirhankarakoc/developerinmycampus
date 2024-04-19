package com.emirhankarakoc.Authorization.companies;

import com.emirhankarakoc.Authorization.companies.DTOS.CreateCompanyRequest;
import com.emirhankarakoc.Authorization.companies.DTOS.CreateCompanyResponse;
import com.emirhankarakoc.Authorization.users.User;

import java.util.List;

public interface CompanyService {

    List<CompanyDTO> getAllCompanies();
    CreateCompanyResponse createCompany(CreateCompanyRequest request, User companyOwner);
}
