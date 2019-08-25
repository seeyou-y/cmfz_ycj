package com.baizhi.service;

import com.baizhi.dao.ProvinceDAO;
import com.baizhi.entity.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class ProvinceServiceImpl implements ProvinceService {
    @Autowired
    private ProvinceDAO provinceDAO;

    @Override
    public List<Province> findAllProvince() {
        return provinceDAO.selectAll();
    }
}
