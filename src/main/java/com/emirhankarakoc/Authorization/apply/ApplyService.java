package com.emirhankarakoc.Authorization.apply;

import com.emirhankarakoc.Authorization.apply.DTOS.CreateApplicationRequest;

import java.util.List;

public interface ApplyService {
    Apply createApplication(CreateApplicationRequest request);
    List<Apply> getAllApplications();
}
