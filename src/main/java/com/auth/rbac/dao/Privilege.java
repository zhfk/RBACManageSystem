package com.auth.rbac.dao;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Privilege {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "`name`")
    private String name;

    @NotNull
    @Column(name = "`resource`")
    private String resource;

    @Column(name = "`desc`")
    private String desc;
}
