package com.emirhankarakoc.Authorization.users;


import com.emirhankarakoc.Authorization.exceptions.general.NotfoundException;
import com.emirhankarakoc.Authorization.githubHelper.GithubService;
import com.emirhankarakoc.Authorization.users.preferances.ChangePreferencesRequest;
import com.emirhankarakoc.Authorization.users.preferances.Preferences;
import com.emirhankarakoc.Authorization.users.preferances.PreferencesRepository;
import com.emirhankarakoc.Authorization.users.preferances.ShareMyDetails;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.emirhankarakoc.Authorization.users.User.*;

@AllArgsConstructor
@Service
@Slf4j
public class UserManager implements UserService{
    private final UserRepository repository;
    private final PreferencesRepository preferencesRepository;
    private final GithubService githubService;


    public List<UserDTO> getAll(){
        //token validasyonlari controllerda yapilmaktadir.
            return userListToDTO(repository.findAll());
    }
    public List<UserDTO> getAllUsersByAccountStatusIsReady(){
        return userListToDTO(repository.findAllByAccountStatus(AccountStatus.READY));

    }
    public List<UserDTO> getAllUsersByAccountStatusIsCreated(){
        return userListToDTO(repository.findAllByAccountStatus(AccountStatus.CREATED));
    }
    public List<UserDTO> getAllUsersByAccountStatusIsBanned(){
        return userListToDTO(repository.findAllByAccountStatus(AccountStatus.BANNED));
    }


    public String addGithubUsernameToUser(String githubUsername, User user) {
        githubService.initUser(githubUsername,user);
        updateUser(user);
    return "Added.";
    }
    public String addGithubRepositoriesToUser(User user){

        githubService.fetchRepositories(user.getGithubAccount());
        updateUser(user);
        return "Successful";
    }
    public UserDTO getUser(String id){

        User user = repository.findById(id).orElseThrow(()-> new NotfoundException("User not found"));
        validateUserStatus(user);
        UserDTO response = userToDTO(user);
        String sayHidden = "***HIDDEN***" ;
        if (user.getPreferences().getMail().equals(ShareMyDetails.DENY)) response.setMail(sayHidden);
        if (user.getPreferences().getBirthdate().equals(ShareMyDetails.DENY)) response.setMail(sayHidden);
        if (user.getPreferences().getPhoneNumber().equals(ShareMyDetails.DENY)) response.setMail(sayHidden);
        return response;
    }

    public void setPreferences(ChangePreferencesRequest request){
        User user = repository.findByToken(request.getToken()).orElseThrow(()->new NotfoundException("User not found"));
        validateUserStatus(user);
        Preferences usersPreferences = user.getPreferences();
        usersPreferences.setBirthdate(request.getBirthdate());
        usersPreferences.setPhoneNumber(request.getPhoneNumber());
        usersPreferences.setMail(request.getMail());
        preferencesRepository.save(usersPreferences);
        repository.save(user);

    }
}
