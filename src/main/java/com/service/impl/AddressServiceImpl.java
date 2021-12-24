package com.service.impl;

import com.repository.AddressRepository;
import com.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.model._Address;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository repo;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.repo = addressRepository;
    }

    @Override
    public _Address findById(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public _Address save(_Address obj) {
        return repo.save(obj);
    }

    @Override
    public _Address deleteById(Long id) {
        _Address obj = findById(id);
        repo.delete(obj);
        return obj;
    }

    @Override
    public List<_Address> findAll(int page) {
        return repo.findAll(PageRequest.of(page, 30)).toList();
    }

    @Override
    public int getTotalPage() {
        return repo.findAll(Pageable.ofSize(30)).getTotalPages();
    }
}
