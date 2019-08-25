package com.baizhi.controller;

import com.baizhi.entity.Province;
import com.baizhi.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("province")
public class ProvinceController {
    @Autowired
    private ProvinceService provinceService;

    @RequestMapping("findAllProvince")
    public List<Province> findAllProvince() {
        return provinceService.findAllProvince();
    }
}
