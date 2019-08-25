package com.baizhi.service;

import com.baizhi.entity.Guru;

import java.util.List;
import java.util.Map;

public interface GuruService {
    List<Guru> findAll();

    Map<String, Object> findAllGuruByPage(Integer page, Integer rows);

    Map<String, Object> operGuru(String oper, Guru guru);

    Map<String, Object> updateGuru(Guru guru);
}
