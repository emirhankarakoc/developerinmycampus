package com.emirhankarakoc.Authorization.users.preferances;

import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class ChangePreferencesRequest {
    private String token;
    private ShareMyDetails phoneNumber;
    private ShareMyDetails mail;
    private ShareMyDetails birthdate;
}
