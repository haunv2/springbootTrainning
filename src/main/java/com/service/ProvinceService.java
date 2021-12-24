package com.service;

import com.model.Province;

import java.util.List;

public interface ProvinceService {

    Province findById(Integer id);

    Province save(Province obj);

    Province deleteById(Integer id);

    List<Province> findAll(int page);

    int getTotalPage();
}
