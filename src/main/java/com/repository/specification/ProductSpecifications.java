package com.repository.specification;

import com.model.Product;
import com.repository.specification.model.ProductFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecifications {

    public static Specification<Product> byName(String q) {
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("name"), "%".concat(q.concat("%")));
            }
        };
    }

    public static Specification<Product> inYear(Integer year) {
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("year"), year);
            }
        };
    }

    public static Specification<Product> inSeason(String season) {
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("season"), season);
            }
        };
    }

    public static Specification<Product> priceBetween(Integer min, Integer max) {
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.between(root.get("price"), min, max);
            }
        };
    }

    public static Specification<Product> filter(ProductFilter filter) {
        List<Specification> p = new ArrayList<>();

        if (filter.getYear() != null)
            p.add(inYear(filter.getYear()));
        if (filter.getSeason() != null)
            p.add(inSeason(filter.getSeason()));
        if (filter.getMinPrice() != null && filter.getMaxPrice() != null) {
            p.add(priceBetween(filter.getMinPrice(), filter.getMaxPrice()));
        }

        Specification specs = p.get(0);
        p.remove(0);

        for (Specification s : p)
            specs.and(s);

        return p == null ? null : Specification.where(specs);
    }

}
