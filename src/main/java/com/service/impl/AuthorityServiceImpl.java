package com.service.impl;

import com.model.Authority;
import com.repository.AuthorityRepository;
import com.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository repo;

    @Autowired
    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.repo = authorityRepository;
    }

    @Override
    public Authority findById(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public Authority save(Authority obj) {
        return repo.save(obj);
    }

    @Override
    public Authority deleteById(Long id) {
        Authority obj = findById(id);
        repo.delete(obj);
        return obj;
    }

    @Override
    public List<Authority> findAll(int page)  {
        return repo.findAll(PageRequest.of(page, 30)).toList();
    }

    @Override
    public int getTotalPage() {
        return repo.findAll(Pageable.ofSize(30)).getTotalPages();
    }
}
