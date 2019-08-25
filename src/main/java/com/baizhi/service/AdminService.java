package com.baizhi.service;

import com.baizhi.entity.Admin;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


public interface AdminService {
    Map<String, Object> Adminlogin(Admin admin, HttpServletRequest request);
}
