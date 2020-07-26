package com.lyx.dao;

import com.lyx.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IUserDao {
    User findUserByUserName(String username);
    List<User> findAll(@Param("start")int start,@Param("size")int size,@Param("username")String username);

    void deleteById(int id);

    void add(User user);

    User selectUserById(int id);

    void update(User user);
    int getTotalCount(@Param("username")String username);

    void deleteAll(@Param("ids") List<Integer> ids);
}
