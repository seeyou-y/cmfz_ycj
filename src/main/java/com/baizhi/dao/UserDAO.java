package com.baizhi.dao;

import com.baizhi.entity.User;
import com.baizhi.vo.UserSexCount;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDAO extends Mapper<User> {
    Integer selectCountByDays(@Param("days") Integer days);

    List<UserSexCount> findBoyGroupByProcince();

    List<UserSexCount> findGrilGroupByProcince();
}
