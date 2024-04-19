package com.emirhankarakoc.Authorization.companies.DTOS;

import com.emirhankarakoc.Authorization.users.User;
import com.emirhankarakoc.Authorization.users.UserDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateCompanyResponse {
    private String id;
    private String fullname;//sirketin ismi
    private LocalDateTime createddatetime;
    private LocalDateTime updateddatetime;
    private List<UserDTO> employeeList;
    private UserDTO ownerUser;
}
