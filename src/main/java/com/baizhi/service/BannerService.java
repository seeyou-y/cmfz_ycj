package com.baizhi.service;

import com.baizhi.entity.Banner;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public interface BannerService {
    Map<String, Object> showAllByPage(Integer page, Integer rows);

    Map<String, Object> operBanner(HttpServletRequest request, String oper, Banner banner) throws IOException;

    Map<String, Object> updateBanner(String id, String cover);
}
