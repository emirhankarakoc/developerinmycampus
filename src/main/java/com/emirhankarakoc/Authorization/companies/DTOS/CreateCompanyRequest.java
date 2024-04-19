package com.emirhankarakoc.Authorization.companies.DTOS;

import lombok.Data;

@Data
public class CreateCompanyRequest {
    private String fullname;
    private String token;
}
