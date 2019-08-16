package com.auth.rbac.dao;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Rtree {
    private String name;
    private Integer value;
    private List<Rtree> children;
}
