package com.controller;

import com.jwtConfig.jwtModel.JwtRequest;
import com.jwtConfig.jwtUtil.JwtTokenUtil;
import com.model.ResponseData;
import com.model.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/user")
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('admin', 'user')")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(new ResponseData(service.findById(id),
                null, null));
    }

    @GetMapping("/getAll")
//    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        return ResponseEntity.ok(new ResponseData(service.findAll(null, page),
                page,
                page < service.count(null).intValue())
        );
    }

    @RequestMapping(value = "/", method = {RequestMethod.POST, RequestMethod.PUT}, produces = {"application/json"}, consumes = {"application/json"})
    @Transactional
//    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> save(@Valid @RequestBody User obj) {
        System.out.println("user == null " + obj);

        return ResponseEntity.ok(new ResponseData(service.save(obj),
                null, null));
    }

    @DeleteMapping("/{id}")
    @Transactional
//    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(new ResponseData(service.deleteById(id), null, null));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody JwtRequest jwtRequest) {
        System.out.println(jwtRequest.getUsername());
        final UserDetails userDetail = null;
                ((UserDetailsService) service).loadUserByUsername("saas");

        if (userDetail == null)
            return new ResponseEntity<>(new ResponseData("error", "Username or password invalid!"), HttpStatus.FORBIDDEN);
        return ResponseEntity.ok(new ResponseData("Bearer ".concat(jwtTokenUtil.generateToken(userDetail)),
                null, null));
    }

    @PostMapping("/register")
    @Transactional
//    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> register(@RequestBody User obj) {
        obj.setId(null);
        return ResponseEntity.ok(new ResponseData(service.save(obj),
                null, null));
    }

}
