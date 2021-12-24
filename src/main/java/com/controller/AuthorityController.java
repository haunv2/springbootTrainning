package com.controller;

import com.model.Authority;
import com.model.Category;
import com.model.ResponseData;
import com.model._Address;
import com.service.AuthorityService;
import com.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/authority")
public class AuthorityController {
    private final AuthorityService service;

    @Autowired
    public AuthorityController(AuthorityService service) {
        this.service = service;
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<?> findById(@PathVariable(value = "id")Long id){
//        ResponseData respone = null;
//        Authority obj = service.findById(id);
//
//        respone = new ResponseData(obj);
//        return new ResponseEntity<>(respone, respone.getCode());
//    }

    @GetMapping("/getAll")
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") String page) {
        ResponseData respone = null;
        Integer p = null;
        try {
            p = Integer.valueOf(page);
        } catch (Exception e) {
            e.printStackTrace();
        }

        respone = new ResponseData(service.findAll(p));
        return new ResponseEntity<>(respone, respone.getCode());
    }
    @GetMapping("/getTotalPage")
    public ResponseEntity<?> getTotalPage(){
        ResponseData respone = null;

        respone = new ResponseData(service.getTotalPage());
        return new ResponseEntity<>(respone, respone.getCode());
    }

}
