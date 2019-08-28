package com.baizhi.controller;

import com.baizhi.api.CustomConstant;
import com.baizhi.api.MesResponse;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import com.baizhi.util.ImageUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class AdminController extends MesResponse {
    @Autowired
    private AdminService adminService;

    /**
     * 登录   验证码
     *
     * @param admin
     * @param code
     * @param request
     * @return
     */
    @RequestMapping("login")
    public Map<String, Object> adminLogin(Admin admin, String code, HttpServletRequest request) {
        String attribute = (String) request.getSession().getAttribute("code");
        if (attribute.equals(code)) {
            Subject subject = SecurityUtils.getSubject();
            AuthenticationToken token = new UsernamePasswordToken(admin.getName(),admin.getPassword());
            try {
                subject.login(token);
                return setResultSuceess();
            } catch (UnknownAccountException e) {
                return setResultErrorAndMes("用户不存在");
            }catch (IncorrectCredentialsException e){
                return setResultErrorAndMes("密码错误");
            }
        } else {
            return setResult(CustomConstant.HTTP_RES_CODE_VALUE_500, "验证码错误", null);
        }
    }

   /* *//**
     * 获取登陆者信息
     *
     * @param request
     * @return
     *//*
    @RequestMapping("session")
    public Admin getSession(HttpServletRequest request) {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        return admin;
    }*/

    /**
     * 退出登录 消除session
     *
     * @return
     */
    @RequestMapping("logOut")
    public String outLogin() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return CustomConstant.HTTP_RES_MES_VALUE_SUCCESS;
    }


    /**
     * 验证码
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("getcode")
    private void getcode(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //获取验证码
        char[] randomChar = ImageUtil.getRandomChar();

        //存session
        request.getSession().setAttribute("code", new String(randomChar));
        //将数组写入图片中
        BufferedImage image = ImageUtil.getImage(randomChar);
        //设置图片响应类型
        response.setContentType("image/png");
        //响应图片
        //参数1:输出的图片对象是谁   参数2:输出图片格式  //参数3:以什么样的流输出
        ImageIO.write(image, "png", response.getOutputStream());
    }
}
