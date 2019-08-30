package com.baizhi.service;

import com.baizhi.annotation.RedisCache;
import com.baizhi.api.CustomConstant;
import com.baizhi.api.MesResponse;
import com.baizhi.dao.ArticleDAO;
import com.baizhi.entity.Article;
import com.baizhi.repository.ArticleRepository;
import org.apache.commons.collections4.IterableUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class ArticleServiceImpl extends MesResponse implements ArticleService {
    @Autowired
    private ArticleDAO articleDAO;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    @RedisCache
    public Map<String, Object> selectAllArticleByPage(Integer page, Integer rows) {
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
            //添加到es
            articleRepository.save(article);
            return setResultSuceess();
        } else if (CustomConstant.HTTP_RES_JQGRID_OPER_EDIT.equals(oper)) {
            System.out.println(article.getContent());
            if ("".equals(article.getContent().replace(" ", ""))) {
                article.setContent(null);
            }
            articleDAO.updateByPrimaryKeySelective(article);
            Article selectOne = articleDAO.selectOne(article);
            //覆盖es中的
            articleRepository.save(selectOne);
            return setResultSuceess();
        } else if (CustomConstant.HTTP_RES_JQGRID_OPER_DEL.equals(oper)) {
            articleDAO.deleteByPrimaryKey(article.getId());
            //从es中删除
            articleRepository.deleteById(article.getId());
            return setResultSuceess();
        } else {
            return null;
        }
    }

    /**
     * 利用 Elasticsearch 开启高亮查询
     *
     * @param content
     * @return
     */
    @Override
    public List<Article> searchArticle(String content) {
        //判断搜索条件
        // 空字符 或者 null  时 查询所有
        // 否者 利用自定义的方法查询
        if ("".equals(content) || content == null) {
            Iterable<Article> articleIterable = articleRepository.findAll();
            List<Article> articles = IterableUtils.toList(articleIterable);
            return articles;
        } else {
            //高亮
            HighlightBuilder.Field field = new HighlightBuilder.Field("*")
                    .preTags("<span style='color:red'>")
                    .postTags("</span>")
                    .requireFieldMatch(false);
            //查询
            NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(QueryBuilders.queryStringQuery(content)
                            .field("title").field("guruName").field("content"))
                    .withHighlightFields(field).withSort(SortBuilders.scoreSort()).build();
            AggregatedPage<Article> articles = elasticsearchTemplate.queryForPage(searchQuery, Article.class, new SearchResultMapper() {
                @Override
                public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> aClass, Pageable pageable) {
                    SearchHits hits = response.getHits();
                    ArrayList<Article> list = new ArrayList<>();
                    for (SearchHit hit : hits) {
                        Article article = new Article();
                        //取值值 存入list
                        Map<String, Object> map = hit.getSourceAsMap();
                        article.setId(map.get("id").toString());
                        article.setGuruName(map.get("guruName").toString());
                        article.setTitle(map.get("title").toString());
                        article.setContent(map.get("content").toString());
                        String publishDate = map.get("publishDate").toString();
                        article.setPublishDate(new Date(Long.valueOf(publishDate)));
                        //取高亮
                        Map<String, HighlightField> fields = hit.getHighlightFields();
                        if (fields.get("title") != null) {
                            article.setTitle(fields.get("title").getFragments()[0].toString());
                        }
                        if (fields.get("guruName") != null) {
                            article.setGuruName(fields.get("guruName").getFragments()[0].toString());
                        }
                        if (fields.get("content") != null) {
                            article.setContent(fields.get("content").getFragments()[0].toString());
                        }
                        list.add(article);
                    }
                    return new AggregatedPageImpl<T>((List<T>) list);
                }
            });
            return articles.getContent();
        }
    }
}
