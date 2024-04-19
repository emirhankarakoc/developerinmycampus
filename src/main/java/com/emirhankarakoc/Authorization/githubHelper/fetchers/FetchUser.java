package com.emirhankarakoc.Authorization.githubHelper.fetchers;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class FetchUser {

    @SerializedName("login")
    private String login;

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("public_repos")
    private int public_repos;

    @SerializedName("bio")
    private String bio;
}
