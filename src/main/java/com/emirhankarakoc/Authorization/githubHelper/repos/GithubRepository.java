package com.emirhankarakoc.Authorization.githubHelper.repos;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.boot.convert.DataSizeUnit;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class GithubRepository {
    @Id
    private String id;
    private String name;

    private String language;
    private String description;
    private int commitCounter;
    private double byteSize;
    private Date lastUpdated;

}
