package com.auth.rbac.dao;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "rosource_has_privilege")
public class RosourceHasPrivilege {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "resource", nullable = false, foreignKey = @ForeignKey(name = "FK_Reference_resource"), referencedColumnName = "name")
    private Resource resource;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "privilege", nullable = false, foreignKey = @ForeignKey(name = "FK_Reference_privilege"), referencedColumnName = "name")
    private Privilege privilege;
}

