package com.auth.rbac.dao;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(uniqueConstraints=@UniqueConstraint(columnNames="name"))
public class Role {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "`name`")
    private String name;

    @Column(name = "`desc`")
    private String desc;
}
