package com.baizhi.controller;

import com.baizhi.entity.City;
import com.baizhi.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("city")
public class CityController {
    @Autowired
    private CityService cityService;

    /**
     * 查询所有城市  by provinceid
     *
     * @param provinceId
     * @return
     */
    @RequestMapping("findAllCityByProvinceId")
    public List<City> findAllCityByProvinceId(String provinceId) {
        return cityService.findAllCityByProvinceId(provinceId);
    }
}
