package com.controller;

import com.model.Authority;
import com.model.Province;
import com.model.ResponseData;
import com.model._Address;
import com.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/province")
public class ProvinceController {
    private final ProvinceService service;

    @Autowired
    public ProvinceController(ProvinceService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id")Integer id){
        ResponseData respone = null;
        Province obj = service.findById(id);

        respone = new ResponseData(obj);
        return ResponseEntity.ok(respone);
    }

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

    @PostMapping("/")
    @Transactional
    public ResponseEntity<?> save(@RequestBody Province obj){
        ResponseData respone = null;
        obj = service.save(obj);

        respone = new ResponseData(obj);
        return new ResponseEntity<>(respone, respone.getCode());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable(value = "id")Integer id){
        ResponseData respone = null;

        respone = new ResponseData(service.deleteById(id));
        return new ResponseEntity<>(respone, respone.getCode());
    }
    @GetMapping("/getTotalPage")
    public ResponseEntity<?> getTotalPage(){
        ResponseData respone = null;

        respone = new ResponseData(service.getTotalPage());
        return new ResponseEntity<>(respone, respone.getCode());
    }
}
