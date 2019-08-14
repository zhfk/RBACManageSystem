package com.auth.rbac.dao;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Admin {

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
    private String nativePlace;

    @Column
    private String forSpareTime;

    @Column
    private String gender;

    @Column
    private String personalDescription;

    @Column
    private String roles;

}