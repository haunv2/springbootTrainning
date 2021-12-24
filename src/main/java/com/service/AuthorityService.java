package com.service;

import com.model.Authority;

import java.util.List;

public interface AuthorityService {
    Authority findById(Long id);

    Authority save(Authority obj);

    Authority deleteById(Long id);

    List<Authority> findAll(int page);

    int getTotalPage();
}
