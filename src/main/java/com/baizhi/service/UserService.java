package com.baizhi.service;

import com.baizhi.entity.User;

import java.util.Map;

public interface UserService {

    Map<String, Object> selectAllUserByPage(Integer page, Integer rows);

    Map<String, Object> operUser(String oper, User user);

    Map<String, Object> updateUser(User user);

    Map<String, Object> selectCountByDays();

    Map<String, Object> selectCountByProvinceAndSex();
}
