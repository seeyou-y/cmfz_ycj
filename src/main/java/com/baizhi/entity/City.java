package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
@Accessors(chain = true)
@Table(name = "cmfz_city")
public class City implements Serializable {
    @Id
    @Excel(name = "ID")
    private String id;
    @Excel(name = "Name")
    private String code;
    @Excel(name = "Code")
    private String name;
    @Excel(name = "ProvinceId")
    private String provinceId;
}
