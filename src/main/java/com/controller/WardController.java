package com.controller;

import com.model.ResponseData;
import com.model.Ward;
import com.repository.specification.WardSpecifications;
import com.service.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;


@RestController
@RequestMapping(value = "api/ward")
public class WardController {

    private final WardService service;

    @Autowired
    public WardController(WardService wardService) {
        this.service = wardService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok(new ResponseData(service.findById(id),
                null, null));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                     @RequestParam(value = "district", required = false) Integer district) {
        Specification specs = null;
        if (district != null)
            specs = WardSpecifications.byDistrict(district);
        return ResponseEntity.ok(new ResponseData(service.findAll(specs, page),
                page,
                page < service.count(null).intValue())
        );
    }

    @RequestMapping(value = "/", method = {RequestMethod.POST, RequestMethod.PUT}, produces = {"application/json"}, consumes = {"application/json"})
    @Transactional
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> save(@Valid @RequestBody Ward obj) {
        obj.setDistrict(null);
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
