package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.Map;

public interface ArticleService {
    Map<String, Object> findAllArticleByPage(Integer page, Integer rows);

    Map<String, Object> operArticle(String oper, Article article);
}
