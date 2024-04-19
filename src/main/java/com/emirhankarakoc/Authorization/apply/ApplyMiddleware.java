package com.emirhankarakoc.Authorization.apply;

import com.emirhankarakoc.Authorization.apply.DTOS.CreateApplicationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ApplyMiddleware {
    private final ApplyService service;
    private final ApplicationsRepository repository;
    public List<Apply> getAllApplications(){
        return service.getAllApplications();
    }
    public Apply createApplication(CreateApplicationRequest r){
        return service.createApplication(r);
    }
}
