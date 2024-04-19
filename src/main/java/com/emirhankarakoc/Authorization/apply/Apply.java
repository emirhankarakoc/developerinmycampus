package com.emirhankarakoc.Authorization.apply;


import com.emirhankarakoc.Authorization.companies.Company;
import com.emirhankarakoc.Authorization.users.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Apply {
    @Id
    private String id;
    @Enumerated
    private ApplicationStatus status;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User applicant;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;





}
