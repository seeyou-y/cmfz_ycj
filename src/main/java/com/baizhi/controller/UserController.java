package com.baizhi.controller;


import com.alibaba.fastjson.JSON;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("findAllUserByPage")
    public Map<String, Object> findAllUserByPage(Integer page, Integer rows) {
        return userService.selectAllUserByPage(page, rows);
    }

    /**
     * @param oper
     * @param user
     * @return
     */
    @RequestMapping("operUser")
    public Map<String, Object> operUser(String oper, User user) {
        Map<String, Object> map = userService.operUser(oper, user);
        Map<String, Object> map1 = userService.selectCountByDays();
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-5ef79b4d44d2467799f085015a79dff8");
        goEasy.publish("my_channel", JSON.toJSONString(map1));
        return map;
    }

    /**
     * @param photo
     * @param id
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("upload")
    public Map<String, Object> uploadPic(MultipartFile photo, String id, HttpServletRequest request) throws IOException {
        System.out.println(photo.getOriginalFilename());
        if (!"".equals(photo.getOriginalFilename())) {
            String realPath = request.getSession().getServletContext().getRealPath("/view/user/image");
            photo.transferTo(new File(realPath, photo.getOriginalFilename()));//文件上传
            User user = new User();
            user.setId(id).setPhoto(photo.getOriginalFilename());
            return userService.updateUser(user);
        } else {
            return null;
        }
    }

    @RequestMapping("getCountByDays")
    public Map<String, Object> getCountByDays() {
        return userService.selectCountByDays();
    }

    @RequestMapping("getProvinceBySex")
    public Map<String, Object> getProvinceBySex() {
        return userService.selectCountByProvinceAndSex();
    }

}
