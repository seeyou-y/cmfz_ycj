package com.baizhi.service;

import com.baizhi.api.CustomConstant;
import com.baizhi.api.MesResponse;
import com.baizhi.dao.GuruDAO;
import com.baizhi.entity.Guru;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class GuruServiceImpl extends MesResponse implements GuruService {
    @Autowired
    private GuruDAO guruDAO;

    @Override
    public List<Guru> findAll() {
        return guruDAO.selectAll();
    }

    @Override
    public Map<String, Object> findAllGuruByPage(Integer page, Integer rows) {
        List<Guru> gurus = guruDAO.selectByRowBounds(new Guru(), setRowBound(page, rows));
        int count = guruDAO.selectCount(new Guru());
        return setJqgridMap(page, count, rows, gurus);
    }

    @Override
    public Map<String, Object> updateGuru(Guru guru) {
        guruDAO.updateByPrimaryKeySelective(guru);
        return setResultSuceess();
    }

    @Override
    public Map<String, Object> operGuru(String oper, Guru guru) {
        if (CustomConstant.HTTP_RES_JQGRID_OPER_ADD.equals(oper)) {
            guru.setId(UUID.randomUUID().toString().replace("-", ""))
                    .setCreateDate(new Date());
            guruDAO.insertSelective(guru);
            return setResultSuceessAndData(guru.getId());
        } else if (CustomConstant.HTTP_RES_JQGRID_OPER_EDIT.equals(oper)) {
            if ("".equals(guru.getPhoto())) {
                guru.setPhoto(null);
            }
            guruDAO.updateByPrimaryKeySelective(guru);
            return setResultSuceessAndData(guru.getId());
        } else if (CustomConstant.HTTP_RES_JQGRID_OPER_DEL.equals(oper)) {
            guruDAO.deleteByPrimaryKey(guru.getId());
            return setResultSuceess();
        } else {
            return null;
        }
    }
}
