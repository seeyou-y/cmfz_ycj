package com.baizhi.controller;

import com.baizhi.api.MesResponse;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("banner")
public class BannerController extends MesResponse {
    @Autowired
    private BannerService bannerService;

    /**
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("showAllBanner")
    public Map<String, Object> showAllBanner(Integer page, Integer rows) {
        return bannerService.showAllByPage(page, rows);
    }

    /**
     * @param oper
     * @param banner
     * @param request
     * @return
     */
    @RequestMapping("operBanner")
    public Map<String, Object> operBanner(String oper, Banner banner, HttpServletRequest request) {
        try {
            return bannerService.operBanner(request, oper, banner);
        } catch (IOException e) {
            return setResultError();
        }
    }

    /**
     * 加入if判断为了判断是否长传图片   以防修改时不修改图片而进行上传操作
     *
     * @param cover   必须和上传的属性name对应
     * @param id
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("upload")
    public Map<String, Object> uploadPic(MultipartFile cover, String id, HttpServletRequest request) throws IOException {
        System.out.println(cover.getOriginalFilename());
        if (!"".equals(cover.getOriginalFilename())) {
            String realPath = request.getSession().getServletContext().getRealPath("/view/photo/image");
            cover.transferTo(new File(realPath, cover.getOriginalFilename()));//文件上传
            System.out.println("----------------------");
            return bannerService.updateBanner(id, cover.getOriginalFilename());
        } else {
            return null;
        }
    }
}
