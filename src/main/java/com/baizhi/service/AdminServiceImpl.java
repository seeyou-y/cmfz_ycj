package com.baizhi.service;

import com.baizhi.api.CustomConstant;
import com.baizhi.api.MesResponse;
import com.baizhi.dao.AdminDAO;
import com.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
@Transactional
public class AdminServiceImpl extends MesResponse implements AdminService {
    @Autowired
    private AdminDAO adminDAO;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> Adminlogin(Admin admin, HttpServletRequest request) {
        Admin admin1 = new Admin();
        admin1.setName(admin.getName());
        Admin one = adminDAO.selectOne(admin1);
        if (one != null) {
            if (one.getPassword().equals(admin.getPassword())) {
                request.getSession().setAttribute("admin", one);
                return setResult(CustomConstant.HTTP_RES_CODE_VALUE_200, CustomConstant.HTTP_RES_MES_VALUE_SUCCESS, null);
            } else {
                return setResult(CustomConstant.HTTP_RES_CODE_VALUE_500, "密码错误", null);
            }
        } else {
            return setResult(CustomConstant.HTTP_RES_CODE_VALUE_500, "用户不存在", null);
        }
    }
}
