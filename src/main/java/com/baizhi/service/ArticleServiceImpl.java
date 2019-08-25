package com.baizhi.service;

import com.baizhi.api.CustomConstant;
import com.baizhi.api.MesResponse;
import com.baizhi.dao.ArticleDAO;
import com.baizhi.dao.GuruDAO;
import com.baizhi.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class ArticleServiceImpl extends MesResponse implements ArticleService {
    @Autowired
    private ArticleDAO articleDAO;
    @Autowired
    private GuruDAO guruDAO;

    @Override
    public Map<String, Object> findAllArticleByPage(Integer page, Integer rows) {
        List<Article> articles = articleDAO.selectByRowBounds(new Article(), setRowBound(page, rows));
        int count = articleDAO.selectCount(new Article());
        return setJqgridMap(page, count, rows, articles);
    }

    @Override
    public Map<String, Object> operArticle(String oper, Article article) {
        if (CustomConstant.HTTP_RES_JQGRID_OPER_ADD.equals(oper)) {
            article.setId(UUID.randomUUID().toString().replace("-", ""))
                    .setPublishDate(new Date());
            articleDAO.insertSelective(article);
            return setResultSuceess();
        } else if (CustomConstant.HTTP_RES_JQGRID_OPER_EDIT.equals(oper)) {
            System.out.println(article.getContent());
            if ("".equals(article.getContent().replace(" ", ""))) {
                article.setContent(null);
            }
            articleDAO.updateByPrimaryKeySelective(article);
            return setResultSuceess();
        } else if (CustomConstant.HTTP_RES_JQGRID_OPER_DEL.equals(oper)) {
            articleDAO.deleteByPrimaryKey(article.getId());
            return setResultSuceess();
        } else {
            return null;
        }
    }
}
