package com.baizhi.controller;

import com.baizhi.entity.Guru;
import com.baizhi.service.GuruService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("guru")
public class GuruController {
    @Autowired
    private GuruService guruService;

    /**
     * @return
     */
    @RequestMapping("findAllGuru")
    public List<Guru> findAllGuru() {
        return guruService.findAll();
    }

    /**
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("findAllGuruByPage")
    public Map<String, Object> findAllGuruByPage(Integer page, Integer rows) {
        return guruService.findAllGuruByPage(page, rows);
    }

    /**
     * @param oper
     * @param guru
     * @return
     */
    @RequestMapping("operGuru")
    public Map<String, Object> operGuru(String oper, Guru guru) {
        return guruService.operGuru(oper, guru);
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
            String realPath = request.getSession().getServletContext().getRealPath("/view/guru/image");
            photo.transferTo(new File(realPath, photo.getOriginalFilename()));//文件上传
            Guru guru = new Guru();
            guru.setId(id).setPhoto(photo.getOriginalFilename());
            return guruService.updateGuru(guru);
        } else {
            return null;
        }
    }
}
