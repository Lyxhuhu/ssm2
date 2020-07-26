package com.lyx.service.impl;

import com.lyx.bean.Role;
import com.lyx.bean.UserRole;
import com.lyx.dao.IRoleDao;
import com.lyx.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleDao iRoleDao;
    @Override
    public List<Integer> findRoleByUserId(int id) {



        return iRoleDao.findRoleIdsByUserId(id);
    }

    @Override
    public List<Role> findNotRoleByUserId(int id) {
        List<Role> roles=iRoleDao.findNotRoleByUserId(id);
        return roles;
    }

    @Override
    public void addRole(List<Integer> ids,int userId) {
        for(int i:ids){
            UserRole userRole=new UserRole();
            userRole.setRoleId(i);
            userRole.setUserId(userId);
            iRoleDao.addRole(userRole);

        }
    }
}
