package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cmfz_chapter")
@Accessors(chain = true)
public class Chapter implements Serializable {
    @ExcelIgnore
    @Id
    private String id;
    @Excel(name = "专辑名")
    private String title;
    @ExcelIgnore
    private String audio;
    @ExcelIgnore
    private String albumId;
    @Excel(name = "音频大小")
    private String size;
    @Excel(name = "播放时长")
    private String duration;
}