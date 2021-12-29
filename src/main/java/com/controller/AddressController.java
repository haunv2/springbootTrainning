package com.controller;

import com.model.ResponseData;
import com.model._Address;
import com.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/address")
public class AddressController {
    private final AddressService service;

    @Autowired
    public AddressController(AddressService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
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
    public ResponseEntity<?> save(@Valid @RequestBody _Address obj) {
        return ResponseEntity.ok(new ResponseData(service.save(obj),
                null, null));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(new ResponseData(service.deleteById(id), null, null));
    }

}
