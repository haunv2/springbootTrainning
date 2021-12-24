package com.service;

import com.model.Category;
import com.model.Product;

import java.util.List;

public interface CategoryService {
    Category findById(Long id);

    Category save(Category obj);

    Category deleteById(Long id);

    List<Category> findAll(int page);

    int getTotalPage();
}
