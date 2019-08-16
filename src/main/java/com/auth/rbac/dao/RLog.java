package com.auth.rbac.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RLog {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    private String user;

    @Column
    private String action;

    @Column(name = "`timestamp`")
    private Timestamp timestamp;

    @Column(name = "`desc`")
    private String desc;

    public RLog(String user, String action, Timestamp timestamp, String desc) {
        this.user = user;
        this.action = action;
        this.timestamp = timestamp;
        this.desc = desc;
    }
}
