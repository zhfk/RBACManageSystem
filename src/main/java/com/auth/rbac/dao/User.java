package com.auth.rbac.dao;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column
    private String username;

    @Column
    private String organization;

    @Column
    private String email;

    @Column
    private String gender;

    @Column
    private String description;


}
