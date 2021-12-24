package com.service;

import com.model.User;

import java.util.List;

public interface UserService {
    User findById(Long id);

    User save(User obj);

    User deleteById(Long id);

    List<User> findAll(int page);

    int getTotalPage();
}
