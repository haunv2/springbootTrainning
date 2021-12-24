package com.service.impl;

import com.model.Province;
import com.repository.ProvinceRepository;
import com.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceRepository repo;

    @Autowired
    public ProvinceServiceImpl(ProvinceRepository provinceRepository) {
        this.repo = provinceRepository;
    }

    @Override
    public Province findById(Integer id) {
        return repo.findById(id).get();
    }

    @Override
    public Province save(Province obj) {
        return repo.save(obj);
    }

    @Override
    public Province deleteById(Integer id) {
        Province obj = findById(id);
        repo.delete(obj);
        return obj;
    }

    @Override
    public List<Province> findAll(int page) {
        return repo.findAll(PageRequest.of(page, 30)).toList();
    }

    @Override
    public int getTotalPage() {
        return repo.findAll(Pageable.ofSize(30)).getTotalPages();
    }
}
