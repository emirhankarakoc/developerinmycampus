package com.emirhankarakoc.Authorization.users.preferances;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Preferences {
    @Id
    private String id;
    @Enumerated

    private ShareMyDetails phoneNumber;
    @Enumerated
    private ShareMyDetails mail;
    @Enumerated
    private ShareMyDetails birthdate;

    public static Preferences createSimplePreferences(){
        Preferences preferences = new Preferences();
        preferences.setId(UUID.randomUUID().toString());
        preferences.setPhoneNumber(ShareMyDetails.ALLOW);
        preferences.setMail(ShareMyDetails.ALLOW);
        preferences.setBirthdate(ShareMyDetails.ALLOW);
        return preferences;
    }
}
