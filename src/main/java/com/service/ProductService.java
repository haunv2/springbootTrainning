package com.service;

import com.model.Product;
import com.repository.specification.model.ProductFilter;

import java.util.List;

public interface ProductService {
    Product findById(Long id);

    Product save(Product obj);

    Product deleteById(Long id);

    List<Product> findAll(int page);

    List<Product> filter(ProductFilter filter);

    int getTotalPage();
}
