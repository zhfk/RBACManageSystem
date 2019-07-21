package com.auth.rbac.dao;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @Column
    private String birthday;

    @Column
    private String hobby;

    @Column
    private String aphorism;

    @Column
    private String province;

    @Column
    private String city;

    @Column
    private String country;

    @Column
    private String forSpareTime;

    @Column
    private String sex;

    @Column
    private String personalDescription;

}