package com.emirhankarakoc.Authorization.users;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.emirhankarakoc.Authorization.companies.Company;
import com.emirhankarakoc.Authorization.exceptions.general.BadRequestException;
import com.emirhankarakoc.Authorization.githubHelper.account.GithubAccount;
import com.emirhankarakoc.Authorization.githubHelper.repos.GithubRepository;
import com.emirhankarakoc.Authorization.users.preferances.Preferences;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class User {
    @Id
    private String id;
    private String mail;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private LocalDateTime createddatetime;
    private LocalDateTime updateddatetime;
    private String companyId;
    private LocalDateTime birthDate;
    private String token;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "github_accountId")
    private GithubAccount githubAccount;
    @OneToMany
    @JoinColumn(name = "companyId")
    private List<Company> workedCompanies;

    @OneToMany
    @JoinColumn(name = "github_repositoryId")
    private List<GithubRepository> repositories;
    @OneToOne
    @JoinColumn(name = "preferencesId")
    private Preferences preferences;


    @Enumerated
    private List<UserType> userTypeList;

    @Enumerated
    private AccountStatus accountStatus;
    @Enumerated
    private List<VerificationStatus> finishedVerifications;

    @Enumerated
    private List<Branches> interestedBranches;





    public static UserDTO userToDTO(User user){
        return UserDTO.builder()
                .id(user.id)
                .mail(user.mail)
                .username(user.username)
                .firstname(user.firstname)
                .lastname(user.lastname)
                .createddatetime(user.createddatetime)
                .updateddatetime(user.updateddatetime)
                .companyId(user.companyId)
                .workedCompanies(user.workedCompanies)
                .userTypeList(user.userTypeList)
                .birthDate(user.birthDate)
                .githubAccount(user.githubAccount)
                .preferences(user.preferences)

                .build();
    }
    public static List<UserDTO> userListToDTO(List<User> u)
    {
        List<UserDTO> response = new ArrayList<>();
        for (User user: u){
            response.add(userToDTO(user));
        }
        return response;
    }
    public static User createSimpleUser(RegisterRequest request){
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setMail(request.getMail());
        user.setCreateddatetime(LocalDateTime.now());
        user.setUsername(request.getUsername());
        // Şifreyi BCrypt ile hashle
        String hashedPassword = BCrypt.withDefaults().hashToString(12, request.getPassword().toCharArray());
        user.setPassword(hashedPassword);
        // Diğer bilgileri doldur
        user.setFirstname(request.getFirstname());
        user.setGithubAccount(null);
        user.setPreferences(new Preferences());
        user.setLastname(request.getLastname());
        user.setBirthDate(request.getBirthDate());
        user.setUserTypeList(new ArrayList<>());
        user.getUserTypeList().add(request.getUserType());
        user.setInterestedBranches(new ArrayList<>());

        user.setFinishedVerifications(new ArrayList<>());
        user.getFinishedVerifications().add(VerificationStatus.NONE);
        return user;
    }

    public static void updateUser(User user){
        user.setUpdateddatetime(LocalDateTime.now());
    }
    public static void validateUserStatus(User user){
        if (!user.getAccountStatus().equals(AccountStatus.READY)) throw new BadRequestException("User must complete registering the system.");
    }
}
