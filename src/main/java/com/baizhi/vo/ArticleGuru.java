package com.baizhi.vo;

import com.baizhi.entity.Article;
import com.baizhi.entity.Guru;
import lombok.Data;
import lombok.experimental.Accessors;


@Data

@Accessors(chain = true)
public class ArticleGuru {
    //这个文件没有咋用到的
    private Guru guru;
    private Article article;
}
