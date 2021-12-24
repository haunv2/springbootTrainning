package com.service.impl;

import com.model.User;
import com.repository.UserRepository;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository repo;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.repo = userRepository;
    }

    @Override
    public User findById(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public User save(User obj) {
        return repo.save(obj);
    }

    @Override
    public User deleteById(Long id)
    {
        User obj = findById(id);
        repo.delete(obj);
        return obj;
    }

    @Override
    public List<User> findAll(int page) {
        return repo.findAll(PageRequest.of(page, 30)).toList();
    }

    @Override
    public int getTotalPage() {
        return repo.findAll(Pageable.ofSize(30)).getTotalPages();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("load username: " + username);

        // use temporary username: username or admin
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ROLE_ADMIN";
            }
        });

        if(username.equalsIgnoreCase("username") || username.equalsIgnoreCase("admin"))
            return new org.springframework.security.core.userdetails.User(username, "$2a$12$0OzKmICYd3DuZ/zQDi0J7eHThEUlCUzZtEYd7J4bkaTpg1r6jr1aq",
                    roles);
        else
            throw new UsernameNotFoundException(username+"Not found");
    }
}
