package com.emirhankarakoc.Authorization.githubHelper.fetchers;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class FetchRepo {
    @SerializedName("name")
    private String name;

    @SerializedName("updated_at")
    private Date lastUpdated;

    @SerializedName("size")
    private double byteSize;
    @SerializedName("language")
    private String mainLanguage;

    @SerializedName("description")
    private String description;
}
