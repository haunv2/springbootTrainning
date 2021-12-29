package com.service.impl;

import com.model.Product;
import com.repository.ProductRepository;
import com.repository.specification.ProductSpecifications;
import com.repository.specification.model.ProductFilter;
import com.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    @Cacheable(key = "#id")
    public Product findById(Long id) {
        return repo.findById(id).get();
    }

    @Override
    @Caching(
            evict = {@CacheEvict(allEntries = true)},
            put = {@CachePut(key = "#obj.id")}
    )
    public Product save(Product obj) {
        return repo.saveAndFlush(obj);
    }

    @Override
    @CacheEvict(allEntries = true)
    public Product deleteById(Long id) {
        Product obj = findById(id);
        repo.delete(obj);
        return obj;
    }

    @Override
    @Cacheable(key = "#page")
    public List<Product> findAll(Specification specs, int page) {
        return repo.findAll(specs, PageRequest.of(page, 30)).toList();
    }

    @Override
    public Long count(Specification specs) {
        return repo.count(specs);
    }
}
