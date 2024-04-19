package com.emirhankarakoc.Authorization.companies;


import com.emirhankarakoc.Authorization.users.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CompanyDTO {
    private String id;
    private String fullname;//sirketin ismi
    private LocalDateTime createddatetime;
    private LocalDateTime updateddatetime;
    private List<User> employeeList;
    private User ownerUser;
}
