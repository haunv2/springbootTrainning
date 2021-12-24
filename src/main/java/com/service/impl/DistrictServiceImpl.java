package com.service.impl;

import com.model.District;
import com.repository.DistrictRepository;
import com.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictServiceImpl implements DistrictService {

    private final DistrictRepository repo;

    @Autowired
    public DistrictServiceImpl(DistrictRepository districtRepository) {
        this.repo = districtRepository;
    }

    @Override
    public District findById(Integer id) {
        return repo.findById(id).get();
    }

    @Override
    public District save(District obj) {
        return repo.save(obj);
    }

    @Override
    public District deleteById(Integer id) {
        District obj = findById(id);
        repo.delete(obj);
        return obj;
    }

    @Override
    public List<District> findAll(int page) {
        return repo.findAll(PageRequest.of(page, 30)).toList();
    }

    @Override
    public int getTotalPage() {
        return repo.findAll(Pageable.ofSize(30)).getTotalPages();
    }
}
