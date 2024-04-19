package com.emirhankarakoc.Authorization.githubHelper;

import com.emirhankarakoc.Authorization.githubHelper.account.GithubAccount;
import com.emirhankarakoc.Authorization.githubHelper.fetchers.FetchCommit;
import com.emirhankarakoc.Authorization.githubHelper.fetchers.FetchRepo;
import com.emirhankarakoc.Authorization.githubHelper.fetchers.FetchUser;
import com.emirhankarakoc.Authorization.githubHelper.repos.GithubRepoRepository;
import com.emirhankarakoc.Authorization.githubHelper.repos.GithubRepository;
import com.emirhankarakoc.Authorization.users.User;
import com.emirhankarakoc.Authorization.users.UserRepository;
import com.emirhankarakoc.Authorization.githubHelper.account.GithubAccountRepository;
import com.emirhankarakoc.Authorization.users.VerificationStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@AllArgsConstructor
@Slf4j
public class GithubManager implements GithubService {
    private final UserRepository userRepository;
    private final GithubAccountRepository accountRepository;
    private final GithubRepoRepository repoRepository;
    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()); // Sistemdeki işlemci çekirdek sayısı kadar thread havuzu oluşturur.



    public void initUser(String username, User user) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FetchService githubService = retrofit.create(FetchService.class);
        Call<FetchUser> call = githubService.getUser(username);

        call.enqueue(new Callback<FetchUser>() {
            @Override
            public void onResponse(Call<FetchUser> call, Response<FetchUser> response) {
                if (response.isSuccessful() && response.body() != null) {

                    FetchUser data = response.body();

                    GithubAccount account = new GithubAccount();
                    account.setId(UUID.randomUUID().toString());
                    account.setBio(data.getBio());
                    account.setAvatarUrl(data.getAvatarUrl());
                    account.setPublic_repos(data.getPublic_repos());
                    account.setRepositories(new ArrayList<>());
                    account.setLogin(data.getLogin());
                    account = accountRepository.save(account); // Kaydet ve geri dönen nesneyi al

                    user.setGithubAccount(account);
                    user.getFinishedVerifications().add(VerificationStatus.GTHUB);
                    userRepository.save(user);

                } else {
                    System.out.println("Request unsuccessful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<FetchUser> call, Throwable t) {
                System.out.println("Request failed: " + t.getMessage());
            }
        });
    }

    @Transactional
    public void fetchRepositories(GithubAccount account) {
        int totalRepos = account.getPublic_repos();
        int threadsToCreate = totalRepos / 4; // Toplam repo sayısını 4'e böler ve her bir thread için oluşturulacak thread sayısını belirler.

        for (int i = 0; i < threadsToCreate; i++) {
            int startIndex = i * 4;
            int endIndex = Math.min((i + 1) * 4, totalRepos); // Son thread için geri kalan repoları alır.

            executorService.execute(() -> {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.github.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                FetchService githubService = retrofit.create(FetchService.class);

                Call<List<FetchRepo>> call = githubService.getRepositoriesInRange(account.getLogin(), startIndex, endIndex);
                call.enqueue(new Callback<List<FetchRepo>>() {
                    @Override
                    public void onResponse(Call<List<FetchRepo>> call, Response<List<FetchRepo>> response) {
                        if (response.isSuccessful()) {
                            for (FetchRepo fetchRepo : response.body()) {
                                GithubRepository repo = new GithubRepository();
                                repo.setId(UUID.randomUUID().toString());
                                repo.setName(fetchRepo.getName());
                                repo.setDescription(fetchRepo.getDescription());
                                repo.setLastUpdated(fetchRepo.getLastUpdated());
                                repo.setLanguage(fetchRepo.getMainLanguage());
                                repo.setByteSize(fetchRepo.getByteSize());
                                // Burada adamın commitlerini gosterici yapmalıyız. Zaten bir kere aldık username'i.
                                log.info("bir tane repo eklendi." + repo.getName());
                                fetchAllCommits(account.getLogin(),repo);
                                repoRepository.save(repo);
                                account.getRepositories().add(repo);
                                accountRepository.save(account);

                            }
                            // Her bir thread'in işini bitirdiğini belirtmek için bir kontrol mekanizması oluşturmak gerekebilir.
                        }
                    }

                    @Override
                    public void onFailure(Call<List<FetchRepo>> call, Throwable throwable) {
                        // Hata durumunda işlem yapma
                    }
                });
            });
        }
    }


    private void fetchAllCommits(String username,GithubRepository repository){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FetchService githubService = retrofit.create(FetchService.class);
        Call<List<FetchCommit>> call = githubService.getAllCommitsByRepository(username,repository.getName());
        call.enqueue(new Callback<List<FetchCommit>>() {
            @Override
            public void onResponse(Call<List<FetchCommit>> call, Response<List<FetchCommit>> response) {
            GithubRepository repo = repository;
            if (response.isSuccessful()){
                log.error(repository.getName()+" reposunun commitleri eklendi.");
                 repository.setCommitCounter(response.body().size());
                 repoRepository.save(repository);
            }
            }

            @Override
            public void onFailure(Call<List<FetchCommit>> call, Throwable throwable) {

            }
        });

    }

}
