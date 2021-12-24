package com.service;

import com.model.District;

import java.util.List;

public interface DistrictService {
    District findById(Integer id);

    District save(District obj);

    District deleteById(Integer id);

    List<District> findAll(int page);

    int getTotalPage();
}
