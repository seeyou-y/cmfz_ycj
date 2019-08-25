package com.baizhi.service;

import com.baizhi.api.CustomConstant;
import com.baizhi.api.MesResponse;
import com.baizhi.dao.AlbumDAO;
import com.baizhi.dao.ChapterDAO;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class ChapterServiceImpl extends MesResponse implements ChapterService {
    @Autowired
    private ChapterDAO chapterDAO;
    @Autowired
    private AlbumDAO albumDAO;


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> findAllChapterByAlbumId(Chapter chapter, Integer page, Integer rows) {
        List<Chapter> chapters = chapterDAO.selectByRowBounds(chapter, setRowBound(page, rows));
        int count = chapterDAO.selectCount(chapter);
        return setJqgridMap(page, count, rows, chapters);
    }

    @Override
    public Map<String, Object> operChapter(String oper, Chapter chapter, String albumId) {
        if (CustomConstant.HTTP_RES_JQGRID_OPER_ADD.equals(oper)) {
            chapter.setId(UUID.randomUUID().toString().replace("-", ""))
                    .setAlbumId(albumId);
            chapterDAO.insertSelective(chapter);
            //修改专辑中音乐数量
            Album album = albumDAO.selectByPrimaryKey(albumId);
            album.setCount(album.getCount() + 1);
            albumDAO.updateByPrimaryKey(album);
            return setResultSuceessAndData(chapter.getId());
        } else if (CustomConstant.HTTP_RES_JQGRID_OPER_EDIT.equals(oper)) {
            if ("".equals(chapter.getAudio())) {
                chapter.setAudio(null);
            }
            chapterDAO.updateByPrimaryKeySelective(chapter);
            return setResultSuceessAndData(chapter.getId());
        } else if (CustomConstant.HTTP_RES_JQGRID_OPER_DEL.equals(oper)) {
            //删除专辑中的音乐 修改专辑中的音乐数量
            Chapter selectOne = chapterDAO.selectOne(chapter);
            Album album = albumDAO.selectByPrimaryKey(selectOne.getAlbumId());
            album.setCount(album.getCount() - 1);
            albumDAO.updateByPrimaryKey(album);
            chapterDAO.deleteByPrimaryKey(chapter.getId());
            return setResultSuceess();
        } else {
            return null;
        }
    }

    @Override
    public Map<String, Object> updateAudio(Chapter chapter) {
        chapterDAO.updateByPrimaryKeySelective(chapter);
        return setResultSuceess();
    }
}
