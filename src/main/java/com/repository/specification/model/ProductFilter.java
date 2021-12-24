package com.repository.specification.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductFilter {
    private Integer year;
    private String season;
    private Integer minPrice;
    private Integer maxPrice;
    private Integer page;
}
