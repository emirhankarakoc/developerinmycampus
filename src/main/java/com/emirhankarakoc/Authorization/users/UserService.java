package com.emirhankarakoc.Authorization.users;

import com.emirhankarakoc.Authorization.users.preferances.ChangePreferencesRequest;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface UserService {

    List<UserDTO> getAllUsersByAccountStatusIsReady();
    List<UserDTO> getAllUsersByAccountStatusIsCreated();
    List<UserDTO> getAllUsersByAccountStatusIsBanned();
    List<UserDTO> getAll();
    String addGithubUsernameToUser(String username,User user);
    String addGithubRepositoriesToUser(User user);
    UserDTO getUser(String id);
    void setPreferences(ChangePreferencesRequest request);


}
