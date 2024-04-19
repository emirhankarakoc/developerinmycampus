package com.emirhankarakoc.Authorization.companies;

import com.emirhankarakoc.Authorization.users.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@Data
public class Company {

    @Id
    private String id;
    private String fullname;//sirketin ismi
    private LocalDateTime createddatetime;
    private LocalDateTime updateddatetime;

    @OneToMany
    @JoinColumn(name = "userId")
    private List<User> employeeList;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User ownerUser;

    public static void updateCompany(Company company){
        company.setUpdateddatetime(LocalDateTime.now());
    }

    public static CompanyDTO companyToDTO(Company company){
        return CompanyDTO.builder()
                .id(company.id)
                .fullname(company.fullname)
                .createddatetime(company.createddatetime)
                .updateddatetime(company.updateddatetime)
                .employeeList(company.employeeList)
                .ownerUser(company.ownerUser)
                .build();
    }
    public static List<CompanyDTO> companiesToDTO(List<Company> companyList){
        List<CompanyDTO> dtoList = new ArrayList<>();
        for(Company company:companyList){
            dtoList.add(companyToDTO(company));
        }
        return dtoList;
    }
}
