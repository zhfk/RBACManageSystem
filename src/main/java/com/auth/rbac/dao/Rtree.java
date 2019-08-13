package com.auth.rbac.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rtree {
    private String name;
    private Integer value;
    private List<Rtree> children;
}
