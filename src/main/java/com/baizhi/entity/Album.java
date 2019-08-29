package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cmfz_album")
@Accessors(chain = true)
public class Album implements Serializable {
    @ExcelIgnore
    @Id
    private String id;
    @Excel(name = "标题", width = 15, needMerge = true)
    private String title;
    @Excel(name = "内容详细", width = 15, needMerge = true)
    private String detail;
    @Excel(name = "作者", width = 15, needMerge = true)
    private String author;
    @Excel(name = "播音", width = 15, needMerge = true)
    private String boradcast;
    @Excel(name = "集数", needMerge = true)
    private Integer count;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date createDate;
    @Excel(name = "封面", type = 2, width = 40, needMerge = true)
    private String cover;
    @Excel(name = "评分", needMerge = true)
    private String score;
    @Transient
    @ExcelCollection(name = "专辑")
    private List<Chapter> chapters;
}