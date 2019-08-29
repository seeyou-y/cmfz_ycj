package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import com.baizhi.vo.FileMes;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * jqGrid 分页查询
     *
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("findAllArticleByPage")
    public Map<String, Object> findAllArticleByPage(Integer page, Integer rows) {
        return articleService.selectAllArticleByPage(page, rows);
    }

    /**
     * jqGrid工具栏增删改
     *
     * @param oper
     * @param article
     * @return
     */
    @RequestMapping("operArticle")
    public Map<String, Object> operArticle(String oper, Article article) {
        return articleService.operArticle(oper, article);
    }

    /**
     * 从电脑本机上传照片
     *
     * @param aa
     * @param request
     * @return
     */
    @RequestMapping("upload")
    public Map<String, Object> upload(MultipartFile aa, HttpServletRequest request) {
        // System.out.println("aaa           :" + aa.getOriginalFilename());
        HashMap<String, Object> map = new HashMap<>();
        try {
            String realPath = request.getSession().getServletContext().getRealPath("/view/article/image/");
            String url = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()
                    + "/view/article/image/" + aa.getOriginalFilename();
            aa.transferTo(new File(realPath, aa.getOriginalFilename()));
            map.put("error", 0);
            map.put("url", url);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            map.put("error", 1);
            map.put("url", null);
            return map;
        }
    }

    /**
     * 本地服务器照片文件的获取
     *
     * @param request
     * @return
     */
    @RequestMapping("cloud")
    public Map<String, Object> cloud(HttpServletRequest request) {
        //获取文件夹的绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/view/article/image/");
        File file = new File(realPath);
        //获取当前文件夹的文件
        File[] files = file.listFiles();
        List<FileMes> list = new ArrayList<>();
        for (File f : files) {
            FileMes fileMes = new FileMes();
            fileMes.setFilesize(f.length());
            fileMes.setIs_dir(f.isDirectory());
            fileMes.setIs_photo(true);
            fileMes.setDir_path(realPath);
            fileMes.setFiletype(FilenameUtils.getExtension(f.getName()));
            fileMes.setFilename(f.getName());
            fileMes.setHas_file(f.isFile());
            fileMes.setDatetime(new Date());
            list.add(fileMes);
        }
        String url = "http://" + request.getServerName() + ":"
                + request.getServerPort() + request.getContextPath() + "/view/article/image/";
        Map<String, Object> map = new HashMap<>();
        map.put("current_url", url);
        map.put("total_count", files.length);
        map.put("file_list", list);
        return map;
    }
}
