package com.baizhi.service;

import com.baizhi.entity.Album;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface AlbumService {
    Map<String, Object> selectAllByPage(Integer page, Integer rows);

    //jqgrid 工具栏 增删改
    Map<String, Object> operAlbum(String oper, Album album);

    //上传修改封面信息
    Map<String, Object> updateCover(Album album);

    //信息导出
    List<Album> findAll(HttpServletRequest request);
}
