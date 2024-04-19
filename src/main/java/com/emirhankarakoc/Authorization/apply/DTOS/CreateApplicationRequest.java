package com.emirhankarakoc.Authorization.apply.DTOS;

import com.emirhankarakoc.Authorization.companies.Company;
import com.emirhankarakoc.Authorization.users.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class CreateApplicationRequest {
    private String applicantToken;

    private String companyId;
}
