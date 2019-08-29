package com.baizhi.service;

import com.baizhi.annotation.RedisCache;
import com.baizhi.api.CustomConstant;
import com.baizhi.api.MesResponse;
import com.baizhi.dao.BannerDAO;
import com.baizhi.entity.Banner;
import org.apache.ibatis.session.RowBounds;
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
public class BannerServiceImpl extends MesResponse implements BannerService {
    @Autowired
    private BannerDAO bannerDAO;

    @Override
    @RedisCache
    public Map<String, Object> selectAllByPage(Integer page, Integer rows) {
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Banner> banners = bannerDAO.selectByRowBounds(new Banner(), rowBounds);
        int records = bannerDAO.selectCount(new Banner());
        Map<String, Object> map = setJqgridMap(page, records, rows, banners);
        return map;
    }

    @Override
    public Map<String, Object> operBanner(HttpServletRequest request, String oper, Banner banner) {
        if (CustomConstant.HTTP_RES_JQGRID_OPER_ADD.equals(oper)) {
            banner.setId(UUID.randomUUID().toString().replace("-", ""))
                    .setCreateDate(new Date())
                    .setLastUpdateDate(new Date());
            bannerDAO.insert(banner);
            return setResultSuceessAndData(banner.getId());
        } else if (CustomConstant.HTTP_RES_JQGRID_OPER_EDIT.equals(oper)) {
            if ("".equals(banner.getCover())) {
                banner.setCover(null);
            }
            banner.setLastUpdateDate(new Date());
            bannerDAO.updateByPrimaryKeySelective(banner);
            return setResultSuceessAndData(banner.getId());
        } else if (CustomConstant.HTTP_RES_JQGRID_OPER_DEL.equals(oper)) {
            bannerDAO.deleteByPrimaryKey(banner.getId());
            return setResultSuceessAndData(banner.getId());
        } else {
            return null;
        }
    }

    @Override
    public Map<String, Object> updateBanner(String id, String cover) {
        Banner banner = new Banner();
        banner.setId(id).setCover(cover);
        bannerDAO.updateByPrimaryKeySelective(banner);
        return setResultSuceess();
    }
}
