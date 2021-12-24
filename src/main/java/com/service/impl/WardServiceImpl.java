package com.service.impl;

import com.model.Ward;
import com.repository.WardRepository;
import com.service.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WardServiceImpl implements WardService {

    private final WardRepository repo;

    @Autowired
    public WardServiceImpl(WardRepository wardRepository) {
        this.repo = wardRepository;
    }

    @Override
    public Ward findById(Integer id) {
        return repo.findById(id).get();
    }

    @Override
    public Ward save(Ward obj) {
        return repo.save(obj);
    }

    @Override
    public Ward deleteById(Integer id) {
        Ward obj = findById(id);
        repo.delete(obj);
        return obj;
    }

    @Override
    public List<Ward> findAll(int page) {
        return repo.findAll(PageRequest.of(page, 30)).toList();
    }

    @Override
    public int getTotalPage() {
        return repo.findAll(Pageable.ofSize(30)).getTotalPages();
    }
}
