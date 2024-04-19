package com.emirhankarakoc.Authorization.accounts;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.emirhankarakoc.Authorization.exceptions.general.BadRequestException;
import com.emirhankarakoc.Authorization.exceptions.general.NotfoundException;
import com.emirhankarakoc.Authorization.jwt.JWTService;
import com.emirhankarakoc.Authorization.users.*;
import com.emirhankarakoc.Authorization.users.preferances.Preferences;
import com.emirhankarakoc.Authorization.users.preferances.PreferencesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.emirhankarakoc.Authorization.users.User.*;
import static com.emirhankarakoc.Authorization.users.preferances.Preferences.createSimplePreferences;

@Service
@AllArgsConstructor
public class AccountService {
    private final UserRepository repository;
    private final JWTService jwtService;
    private final PreferencesRepository preferencesRepository;




    public String login(LoginRequest request){
        User user = repository.findByUsername(request.getUsername()).orElseThrow(()-> new NotfoundException("Username and password do not match."));

        if (BCrypt.verifyer().verify(request.getPassword().toCharArray(), user.getPassword()).verified) {
            user.setToken(jwtService.generateToken(user.getUsername()));
            repository.save(user);
            return user.getToken();
        }
        else{
            throw new NotfoundException("Username and password do not match."); // sifreler eslesmiyorsa.
        }

    }


    public UserDTO register(RegisterRequest request) {
        Optional<User> testUser = repository.findByUsername(request.getUsername());
        if (testUser.isPresent()) {
            throw new BadRequestException("This username already taken.");
        }
        User user = createSimpleUser(request);
        Preferences preferences =createSimplePreferences();
        preferencesRepository.save(preferences);
        user.setPreferences(preferences);
        user.setAccountStatus(AccountStatus.CREATED);
        updateUser(user);
        // Token oluştur ve kullanıcıyı kaydet
        user.setToken(jwtService.generateToken(user.getUsername()));
        return userToDTO(repository.save(user));}
    public UserDTO register2(RegisterRequest request) {
        Optional<User> testUser = repository.findByUsername(request.getUsername());
        if (testUser.isPresent()) {
            throw new BadRequestException("This username already taken.");
        }
        User user = createSimpleUser(request);
        Preferences preferences =createSimplePreferences();
        preferencesRepository.save(preferences);
        user.setAccountStatus(AccountStatus.CREATED);
        user.setPreferences(preferences);

        updateUser(user);
        user.getUserTypeList().add(UserType.SERVER_ADMIN);
        // Token oluştur ve kullanıcıyı kaydet
        user.setToken(jwtService.generateToken(user.getUsername()));
        return userToDTO(repository.save(user));}


}
