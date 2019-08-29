package com.baizhi.service;

import com.baizhi.annotation.RedisCache;
import com.baizhi.api.CustomConstant;
import com.baizhi.api.MesResponse;
import com.baizhi.dao.AlbumDAO;
import com.baizhi.dao.ChapterDAO;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class AlbumServiceImpl extends MesResponse implements AlbumService {
    @Autowired
    private AlbumDAO albumDAO;
    @Autowired
    private ChapterDAO chapterDAO;

    @Override
    @RedisCache
    public Map<String, Object> selectAllByPage(Integer page, Integer rows) {
        List<Album> albums = albumDAO.selectByRowBounds(new Album(), setRowBound(page, rows));
        int count = albumDAO.selectCount(new Album());
        return setJqgridMap(page, count, rows, albums);
    }

    @Override
    public Map<String, Object> operAlbum(String oper, Album album) {
        if (CustomConstant.HTTP_RES_JQGRID_OPER_ADD.equals(oper)) {
            album.setId(UUID.randomUUID().toString().replace("-", ""))
                    .setCreateDate(new Date())
                    .setCount(0);
            albumDAO.insertSelective(album);
            return setResultSuceessAndData(album.getId());
        } else if (CustomConstant.HTTP_RES_JQGRID_OPER_EDIT.equals(oper)) {
            if ("".equals(album.getCover())) {
                album.setCover(null);
            }
            albumDAO.updateByPrimaryKeySelective(album);
            return setResultSuceessAndData(album.getId());
        } else if (CustomConstant.HTTP_RES_JQGRID_OPER_DEL.equals(oper)) {
            albumDAO.deleteByPrimaryKey(album.getId());
            Chapter chapter = new Chapter();
            chapter.setAlbumId(album.getId());
            chapterDAO.delete(chapter);
            return setResultSuceessAndData(album.getId());
        } else {
            return null;
        }
    }

    @Override
    public Map<String, Object> updateCover(Album album) {
        albumDAO.updateByPrimaryKeySelective(album);
        return setResultSuceess();
    }

    @Override
    @RedisCache
    public List<Album> findAll(HttpServletRequest request) {
        List<Album> albums = albumDAO.selectAll();
        for (Album album : albums) {
            Chapter chapter = new Chapter();
            chapter.setAlbumId(album.getId());
            List<Chapter> select = chapterDAO.select(chapter);
            album.setChapters(select);
            String realPath = request.getSession().getServletContext().getRealPath("/view/album/image/");
            album.setCover(realPath + album.getCover());
        }
        return albums;
    }
}
