package com.emirhankarakoc.Authorization.githubHelper;

import com.emirhankarakoc.Authorization.githubHelper.fetchers.FetchCommit;
import com.emirhankarakoc.Authorization.githubHelper.fetchers.FetchRepo;
import com.emirhankarakoc.Authorization.githubHelper.fetchers.FetchUser;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface FetchService {
    @GET("users/{username}")
    Call<FetchUser> getUser(@Path("username") String username);

    @GET("users/{username}/repos")
    Call<List<FetchRepo>> getAllRepos(@Path("username") String username);

    // Belirli bir aralıktaki repoları almak için yeni bir metod
    @GET("users/{username}/repos")
    Call<List<FetchRepo>> getRepositoriesInRange(
            @Path("username") String username,
            @Query("start") int start, // Başlangıç indeksi
            @Query("end") int end);     // Bitiş indeksi


    @GET("repos/{username}/{repoName}/commits")
    Call<List<FetchCommit>> getAllCommitsByRepository(
            @Path("username") String username,
            @Path("repoName") String repositoryName
    );
}
