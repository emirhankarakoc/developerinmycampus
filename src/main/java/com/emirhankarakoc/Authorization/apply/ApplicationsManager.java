package com.emirhankarakoc.Authorization.apply;


import com.emirhankarakoc.Authorization.apply.DTOS.CreateApplicationRequest;
import com.emirhankarakoc.Authorization.companies.Company;
import com.emirhankarakoc.Authorization.companies.CompanyRepository;
import com.emirhankarakoc.Authorization.exceptions.general.NotfoundException;
import com.emirhankarakoc.Authorization.users.User;
import com.emirhankarakoc.Authorization.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ApplicationsManager implements ApplyService {
    private final ApplicationsRepository repository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;


    public Apply createApplication(CreateApplicationRequest request){
        User user = userRepository.findByToken(request.getApplicantToken()).orElseThrow(()-> new NotfoundException("FetchUser not found"));
        Company company = companyRepository.findById(request.getCompanyId()).orElseThrow(()-> new NotfoundException("Company not found"));
        Apply application = new Apply();
        application.setId(UUID.randomUUID().toString());
        application.setApplicant(user);
        application.setCompany(company);
        application.setStatus(ApplicationStatus.WAITING);
        repository.save(application);
        return application;

    }
    public List<Apply> getAllApplications(){
        return repository.findAll();
    }
}
