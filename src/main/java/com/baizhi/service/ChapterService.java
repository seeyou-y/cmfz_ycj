package com.baizhi.service;

import com.baizhi.entity.Chapter;

import java.util.Map;

public interface ChapterService {
    Map<String, Object> findAllChapterByAlbumId(Chapter chapter, Integer page, Integer rows);

    Map<String, Object> operChapter(String oper, Chapter chapter, String albumId);

    //修改MP3文件
    Map<String, Object> updateAudio(Chapter chapter);
}
