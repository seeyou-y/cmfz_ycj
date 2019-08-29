package com.baizhi.controller;

import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;

@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    /**
     * @param chapter
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("findChapterByAlbumId")
    public Map<String, Object> findChapterByAlbumId(Chapter chapter, Integer page, Integer rows) {
        return chapterService.selectAllChapterByAlbumId(chapter, page, rows);
    }

    /**
     * @param oper
     * @param chapter
     * @param albumId
     * @return
     */
    @RequestMapping("operChapter")
    public Map<String, Object> operChapter(String oper, Chapter chapter, String albumId) {
        return chapterService.operChapter(oper, chapter, albumId);
    }

    /**
     * @param audio
     * @param id
     * @param request
     * @return
     * @throws IOException
     * @throws EncoderException
     */
    @RequestMapping("upload")
    public Map<String, Object> uploadPic(MultipartFile audio, String id, HttpServletRequest request) throws IOException, EncoderException {
        System.out.println("----------------------");
        if (!"".equals(audio.getOriginalFilename())) {
            String realPath = request.getSession().getServletContext().getRealPath("/view/chapter/audio");
            audio.transferTo(new File(realPath, audio.getOriginalFilename()));//文件上传
            Encoder encoder = new Encoder();
            File file = new File(realPath, audio.getOriginalFilename());
            MultimediaInfo info = encoder.getInfo(file);
            long duration = info.getDuration();
            int second = (int) (duration / 1000);
            int s = second / 60;
            int m = second % 60;
            String dur = s + ":" + m;
            Chapter chapter = new Chapter();
            double size = file.length() / 1024.0 / 1024;
            DecimalFormat df = new DecimalFormat("#.##");
            double parseDouble = Double.parseDouble(df.format(size));
            chapter.setId(id).setDuration(dur).setSize(parseDouble + "M");
            chapter.setAudio(audio.getOriginalFilename());
            return chapterService.updateAudio(chapter);
        } else {
            return null;
        }

    }
}
