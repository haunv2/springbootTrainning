package com.controller;

import com.model.Product;
import com.model.ResponseData;
import com.repository.specification.ProductSpecifications;
import com.repository.specification.model.ProductFilter;
import com.service.ProductService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping(value = "api/product")
public class ProductController {
    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(new ResponseData(service.findById(id),
                null, null));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        return ResponseEntity.ok(new ResponseData(service.findAll(null, page),
                page,
                page < service.count(null).intValue())
        );
    }

    @RequestMapping(value = "/", method = {RequestMethod.POST, RequestMethod.PUT}, produces = {"application/json"}, consumes = {"application/json"})
    @Transactional
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> save(@Valid @RequestBody Product obj) {
        return ResponseEntity.ok(new ResponseData(service.save(obj),
                null, null));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(new ResponseData(service.deleteById(id), null, null));
    }


    @PostMapping("/filter")
    public ResponseEntity<?> filter(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        ProductFilter filter = new ProductFilter(); // make new instance filter product
        try {
            BeanUtils.populate(filter, request.getParameterMap()); // set value for instance
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        if (filter.getSeason() == null && filter.getMaxPrice() == null && filter.getYear() == null && filter.getMinPrice() == null) {
            return new ResponseEntity<>(new ResponseData("error", "invalid arguments"), HttpStatus.BAD_GATEWAY);
        }
        Specification specs = ProductSpecifications.filter(filter);
        return ResponseEntity.ok(new ResponseData(service.findAll(ProductSpecifications.filter(filter), page), page, page < service.count(specs) - 1));
    }

    @GetMapping("search")
    public ResponseEntity<?> filter(@RequestParam("q") String search, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        Specification specs = ProductSpecifications.byName(search);
        return ResponseEntity.ok(new ResponseData(service.findAll(specs, page), page, page < service.count(specs) - 1));
    }
}