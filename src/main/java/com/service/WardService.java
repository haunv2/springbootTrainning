package com.service;

import com.model.Ward;

import java.util.List;

public interface WardService {

    Ward findById(Integer id);

    Ward save(Ward obj);

    Ward deleteById(Integer id);

    List<Ward> findAll(int page);

    int getTotalPage();
}
