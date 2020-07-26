package com.lyx.service.impl;

import com.lyx.bean.PageInfo;
import com.lyx.bean.User;
import com.lyx.dao.IUserDao;
import com.lyx.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao iUserDao;


    @Override
    public int login(String username, String password) {
        User user=iUserDao.findUserByUserName(username);
        if(user!=null&&user.getPassword().equals(password)){
            return user.getId();
        }
        return -1;
    }

    @Override
    public PageInfo<User> findAll(int currentPage,String username) {
        PageInfo pageInfo=new PageInfo();
        pageInfo.setSize(5);
        int totalCount=iUserDao.getTotalCount(username);
        pageInfo.setTotalCount(totalCount);
        double d=totalCount/5.0;
        int tp=(int)Math.ceil(d);
        pageInfo.setTotalPage(tp);
        if (currentPage<1){
            pageInfo.setCurrentPage(1);
        }else if(currentPage>tp){
            pageInfo.setCurrentPage(tp);
        }else{
            pageInfo.setCurrentPage(currentPage);
        }
        int start=(pageInfo.getCurrentPage()-1)*5;
        List<User> users=iUserDao.findAll(start,pageInfo.getSize(),username);
        pageInfo.setList(users);
        return  pageInfo;
    }

    @Override
    public void deleteById(int id) {
        iUserDao.deleteById(id);
    }

    @Override
    public void add(User user) {
        iUserDao.add(user);
    }

    @Override
    public User selectUserById(int id) {
        return iUserDao.selectUserById(id);
    }

    @Override
    public void update(User user) {
        iUserDao.update(user);
    }

    @Override
    public void deleteAll(List<Integer> ids) {
        iUserDao.deleteAll(ids);

    }
}
