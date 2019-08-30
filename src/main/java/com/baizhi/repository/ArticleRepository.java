package com.baizhi.repository;

import com.baizhi.entity.Article;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

@Configuration
public interface ArticleRepository extends ElasticsearchRepository<Article, String> {
}
