package com.baizhi.service;

import com.baizhi.dao.CityDAO;
import com.baizhi.entity.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class CityServiceImpl implements CityService {
    @Autowired
    private CityDAO cityDAO;

    @Override
    public List<City> findAllCityByProvinceId(String provinceId) {
        City city = new City();
        city.setProvinceId(provinceId);
        return cityDAO.select(city);
    }
}
