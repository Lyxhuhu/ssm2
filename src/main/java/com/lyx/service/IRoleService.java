package com.lyx.service;

import com.lyx.bean.Role;

import java.util.List;

public interface IRoleService {
    List<Integer> findRoleByUserId(int id);

    List<Role> findNotRoleByUserId(int id);

    void addRole(List<Integer> ids,int userId);
}
