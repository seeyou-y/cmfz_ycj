package com.baizhi.service;

import com.baizhi.annotation.RedisCache;
import com.baizhi.api.CustomConstant;
import com.baizhi.api.MesResponse;
import com.baizhi.dao.UserDAO;
import com.baizhi.entity.User;
import com.baizhi.util.Md5UUIDSaltUtil;
import com.baizhi.vo.UserSexCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class UserServiceImpl extends MesResponse implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Override
    @RedisCache
    public Map<String, Object> selectAllUserByPage(Integer page, Integer rows) {
        List<User> users = userDAO.selectByRowBounds(new User(), setRowBound(page, rows));
        int count = userDAO.selectCount(new User());
        return setJqgridMap(page, count, rows, users);
    }

    @Override
    public Map<String, Object> operUser(String oper, User user) {
        if (CustomConstant.HTTP_RES_JQGRID_OPER_ADD.equals(oper)) {
            String salt = Md5UUIDSaltUtil.getSalt();
            String md5Code = Md5UUIDSaltUtil.createMd5Code(user.getPassword() + salt);
            user.setId(UUID.randomUUID().toString().replace("-", ""))
                    .setCreateDate(new Date())
                    .setLastUpdateDate(new Date())
                    .setSalt(salt).setPassword(md5Code);
            userDAO.insertSelective(user);
            return setResultSuceessAndData(user.getId());
        } else if (CustomConstant.HTTP_RES_JQGRID_OPER_EDIT.equals(oper)) {
            //不修改图片时 防止图片误改
            if ("".equals(user.getPhoto())) {
                user.setPhoto(null);
            }
           /* //修改密码   password+salt
            String salt = user.getSalt();
            String md5Code = Md5UUIDSaltUtil.createMd5Code(user.getPassword() + salt);
            user.setPassword(md5Code);*/
            user.setLastUpdateDate(new Date());
            userDAO.updateByPrimaryKeySelective(user);
            return setResultSuceessAndData(user.getId());
        } else if (CustomConstant.HTTP_RES_JQGRID_OPER_DEL.equals(oper)) {
            userDAO.deleteByPrimaryKey(user.getId());
            return setResultSuceess();
        } else {
            return null;
        }
    }

    @Override
    public Map<String, Object> updateUser(User user) {
        userDAO.updateByPrimaryKeySelective(user);
        return setResultSuceess();
    }

    @Override
    public Map<String, Object> selectCountByDays() {
        Map<String, Object> map = new HashMap<>();
        map.put("data1", userDAO.selectCountByDays(7));
        map.put("data2", userDAO.selectCountByDays(14));
        map.put("data3", userDAO.selectCountByDays(21));
        map.put("data4", userDAO.selectCountByDays(30));
        map.put("data5", userDAO.selectCountByDays(60));
        map.put("data6", userDAO.selectCountByDays(183));
        return map;
    }

    @Override
    @RedisCache
    public Map<String, Object> selectCountByProvinceAndSex() {
        HashMap<String, Object> map = new HashMap<>();
        List<UserSexCount> grilGroupByProcince = userDAO.findGrilGroupByProcince();
        List<UserSexCount> boyGroupByProcince = userDAO.findBoyGroupByProcince();
        map.put("boyData", boyGroupByProcince);
        map.put("grilData", grilGroupByProcince);
        return map;
    }
}
