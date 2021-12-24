package com.service.impl;

import com.model.Category;
import com.repository.CategoryRepository;
import com.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repo;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.repo = categoryRepository;
    }

    @Override
    public Category findById(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public Category save(Category obj) {
        return repo.save(obj);
    }

    @Override
    public Category deleteById(Long id) {
        Category obj = findById(id);
        repo.delete(obj);
        return obj;
    }

    @Override
    public List<Category> findAll(int page) {
        return repo.findAll(PageRequest.of(page, 30)).toList();
    }

    @Override
    public int getTotalPage() {
        return repo.findAll(Pageable.ofSize(30)).getTotalPages();
    }
}
