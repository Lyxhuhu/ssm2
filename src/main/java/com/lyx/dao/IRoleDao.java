package com.lyx.dao;

import com.lyx.bean.Role;
import com.lyx.bean.UserRole;

import java.util.List;

public interface IRoleDao {
    List<Integer> findRoleIdsByUserId(int id);

    List<Role> findNotRoleByUserId(int id);

    void addRole(UserRole userRole);
}
