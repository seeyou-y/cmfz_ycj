package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    /**
     * jqGrid的分页展示
     *
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("showAllAlbumByPage")
    public Map<String, Object> showAllAlbumByPage(Integer page, Integer rows) {
        return albumService.findAllByPage(page, rows);
    }

    /**
     * jqGrid工具栏的 增 删 改
     *
     * @param oper
     * @param album
     * @return
     */
    @RequestMapping("operAlbum")
    public Map<String, Object> operAlbum(String oper, Album album) {
        return albumService.operAlbum(oper, album);
    }

    /**
     * 文件的上传
     *
     * @param cover
     * @param id
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("upload")
    public Map<String, Object> uploadPic(MultipartFile cover, String id, HttpServletRequest request) throws IOException {
        System.out.println(cover.getOriginalFilename());
        if (!"".equals(cover.getOriginalFilename())) {
            String realPath = request.getSession().getServletContext().getRealPath("/view/album/image");
            cover.transferTo(new File(realPath, cover.getOriginalFilename()));//文件上传
            System.out.println("----------------------");
            Album album = new Album();
            album.setId(id);
            album.setCover(cover.getOriginalFilename());
            return albumService.updateCover(album);
        } else {
            return null;
        }
    }

    /**
     * 专辑信息的导出
     *
     * @param request
     * @param response
     */
    @RequestMapping("fileOutStream")
    public void fileOutStream(HttpServletRequest request, HttpServletResponse response) {
        List<Album> albums = albumService.findAll(request);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("专辑信息展示", "album"), Album.class, albums);
        try {
            //设置响应 头   编码格式
            response.setHeader("content-disposition", "attachment;fileName=" + URLEncoder.encode("album.xls", "UTF-8"));
            //设置响应类型
            request.getSession().getServletContext().getMimeType("application/vnd.ms-excel");
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
