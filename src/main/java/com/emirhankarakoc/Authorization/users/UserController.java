package com.emirhankarakoc.Authorization.users;


import com.emirhankarakoc.Authorization.exceptions.general.ForbiddenException;
import com.emirhankarakoc.Authorization.exceptions.general.NotfoundException;
import com.emirhankarakoc.Authorization.githubHelper.account.GithubAccount;
import com.emirhankarakoc.Authorization.jwt.JWTService;
import com.emirhankarakoc.Authorization.users.preferances.ChangePreferencesRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.emirhankarakoc.Authorization.users.User.userListToDTO;
import static com.emirhankarakoc.Authorization.users.User.validateUserStatus;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Tag(name = "Users Controller")
@Slf4j
public class UserController {
    private final UserService service;
    private final UserRepository repository;
    private final JWTService jwtService;


    @GetMapping
    @Tag(name = "Kaldirilacak.")
    public List<UserDTO> getAll(String token){
        jwtService.validateToken(token);
        User user = repository.findByToken(token).get();

        if (user.getUserTypeList().contains(UserType.SERVER_ADMIN)) {
            log.info("Adminin tokeni dogru calisiyor ve islem tamamlaniyor");
            return service.getAll();
        } else {
            throw new ForbiddenException("Forbidden");
        }
    }

    @PutMapping("/github/addNickname")
    public String addGithubUsernameToUser(@RequestBody AddGithubUsernameRequest r){
        jwtService.validateToken(r.getToken());
        User user = repository.findByToken(r.getToken()).get();

        return service.addGithubUsernameToUser(r.getUsername(),user);
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable String id){
        return service.getUser(id);
    }
    @GetMapping("/filter/ready")
    public List<UserDTO> getAllUsersByAccountStatusIsReady(){
        return userListToDTO(repository.findAllByAccountStatus(AccountStatus.READY));

    }
    @GetMapping("/filter/created")
//TODO admin token kontrolu
    public List<UserDTO> getAllUsersByAccountStatusIsCreated(){
        return userListToDTO(repository.findAllByAccountStatus(AccountStatus.CREATED));
    }
    @GetMapping("/filter/banned")
//TODO admin token kontrolu

    public List<UserDTO> getAllUsersByAccountStatusIsBanned(){
        return userListToDTO(repository.findAllByAccountStatus(AccountStatus.BANNED));
    }

    @PutMapping("/preferences")
    public void setPreferences(ChangePreferencesRequest request){
        jwtService.validateToken(request.getToken());
        service.setPreferences(request);
    }


    @PutMapping("/repos")
    public void fetchRepositories(String token){
        jwtService.validateToken(token);
         service.addGithubRepositoriesToUser(repository.findByToken(token).get());

    }


    }
