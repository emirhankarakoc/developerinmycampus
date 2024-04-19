package com.emirhankarakoc.Authorization.accounts;

import com.emirhankarakoc.Authorization.exceptions.general.BadRequestException;
import com.emirhankarakoc.Authorization.users.*;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountController {
    private final AccountService service;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String login(@RequestBody LoginRequest r){
        return service.login(r);
    }

    @PostMapping(value = "/register",consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO register(@RequestBody RegisterRequest r) throws IOException, InterruptedException {
        if (r.getUserType().equals(UserType.SERVER_ADMIN)){
            throw new BadRequestException("sen beni gerizekali mi zannettin :)");
        }
        return service.register(r);
    }

    @PostMapping("/admin")
    public UserDTO registerAdmin(@RequestBody RegisterRequest r){
        return service.register2(r);
    }




}
