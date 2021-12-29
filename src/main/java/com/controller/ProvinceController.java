package com.controller;

import com.model.Province;
import com.model.ResponseData;
import com.repository.specification.DistrictSpecifications;
import com.repository.specification.WardSpecifications;
import com.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "api/province")
public class ProvinceController {
    private final ProvinceService service;

    @Autowired
    public ProvinceController(ProvinceService service) {
        this.service = service;
    }

    public ResponseEntity<?> findById(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok(new ResponseData(service.findById(id),
                null, null));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> findAll(@Valid @RequestParam(value = "page", defaultValue = "0") Integer page) {
        return ResponseEntity.ok(new ResponseData(service.findAll(null, page),
                page,
                page < service.count(null).intValue())
        );
    }

    @RequestMapping(value = "/", method = {RequestMethod.POST, RequestMethod.PUT}, produces = {"application/json"}, consumes = {"application/json"})
    @Transactional
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> save(@RequestBody Province obj) {
        return ResponseEntity.ok(new ResponseData(service.save(obj),
                null, null));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasAnyAuthority('admin')")
   public ResponseEntity<?> delete(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok(new ResponseData(service.deleteById(id), null, null));
    }

}
