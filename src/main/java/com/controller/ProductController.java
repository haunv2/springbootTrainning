package com.controller;

import com.model.Product;
import com.model.ResponseData;
import com.repository.specification.model.ProductFilter;
import com.service.ProductService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping(value = "api/product")
public class ProductController {
    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id")Long id){
        ResponseData respone = null;
        Product obj = service.findById(id);

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
    public ResponseEntity<?> save(@RequestBody Product obj){
        ResponseData respone = null;
        obj = service.save(obj);

        respone = new ResponseData(obj);
        return new ResponseEntity<>(respone, respone.getCode());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable(value = "id")Long id){
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

    @PostMapping("/filters")
    public ResponseEntity<?> filter(HttpServletRequest request){
        ProductFilter filter = new ProductFilter();

        filter.setMaxPrice(500);
        filter.setMinPrice(400);
        filter.setSeason("season");

//        try {
//            BeanUtils.populate(filter, request.getParameterMap());
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }

        System.out.println("filter+"+filter);

        ResponseData respone = null;

        respone = new ResponseData(service.filter(filter));
        return new ResponseEntity<>(respone, respone.getCode());
    }

}
