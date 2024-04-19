package com.emirhankarakoc.Authorization.users;

import com.emirhankarakoc.Authorization.companies.Company;
import com.emirhankarakoc.Authorization.githubHelper.account.GithubAccount;
import com.emirhankarakoc.Authorization.users.preferances.Preferences;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class UserDTO {
    private String id;
    private String mail;
    private String username;
    private String firstname;
    private String lastname;
    private GithubAccount githubAccount;
    private LocalDateTime createddatetime;
    private LocalDateTime updateddatetime;
    private String companyId;
    private List<Company> workedCompanies;
    private List<UserType> userTypeList;
    private Preferences preferences;
    private AccountStatus accountStatus;
    private LocalDateTime birthDate;
}
