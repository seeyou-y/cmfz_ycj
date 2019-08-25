package com.baizhi.vo;

import com.baizhi.entity.Article;
import com.baizhi.entity.Guru;
import lombok.Data;
import lombok.experimental.Accessors;


@Data

@Accessors(chain = true)
public class ArticleGuru {
    private Guru guru;
    private Article article;
}
