package com.service.impl;

import com.model.Product;
import com.repository.ProductRepository;
import com.repository.specification.ProductSpecifications;
import com.repository.specification.model.ProductFilter;
import com.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repo;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.repo = productRepository;
    }


    @Override
    public Product findById(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public Product save(Product obj) {
        return repo.save(obj);
    }

    @Override
    public Product deleteById(Long id) {
        Product obj = findById(id);
        repo.delete(obj);
        return obj;
    }

    @Override
    public List<Product> findAll(int page)  {
        return repo.findAll(PageRequest.of(page, 30)).toList();
    }

    @Override
    public List<Product> filter(ProductFilter filter) {
        return repo.findAll(ProductSpecifications.filter(filter),
                PageRequest.of(filter.getPage()==null?0:filter.getPage(), 30)).toList();
    }

    @Override
    public int getTotalPage() {
        return repo.findAll(Pageable.ofSize(30)).getTotalPages();
    }
}
