package com.emirhankarakoc.Authorization.apply;


import com.emirhankarakoc.Authorization.apply.DTOS.CreateApplicationRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/applications")
@AllArgsConstructor
public class ApplyController {
    private final ApplyMiddleware middleware;



    @PostMapping
    public Apply createApplication(CreateApplicationRequest r){
        return middleware.createApplication(r);
    }
    @GetMapping
    public List<Apply> getAllApplications(){
        return middleware.getAllApplications();
    }
}
