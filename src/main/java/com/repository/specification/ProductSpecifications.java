package com.repository.specification;

import com.model.Product;
import com.repository.specification.model.ProductFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ProductSpecifications {

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
        return new Specification<Product>() {

            Specification specification;

            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (filter.getYear() != null)
                    specification.and(inYear(filter.getYear()));
                if (filter.getSeason() != null)
                    specification.and( inSeason(filter.getSeason()));
                if (filter.getMinPrice() != null && filter.getMaxPrice() != null) {
                    specification.and(priceBetween(filter.getMinPrice(), filter.getMaxPrice()));
                }

//                return specification.toPredicate(root, query, criteriaBuilder);
                return (Predicate) priceBetween(filter.getMinPrice(), filter.getMaxPrice());
            }
        };
    }

    public static Specification<Product> testNull(){
        return  new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };
    }

    public static void main(String[] args) {
        ProductFilter filter = new ProductFilter();
        filter.setPage(1);
        filter.setMaxPrice(1313);
        filter.setMinPrice(414);
        filter.setSeason("season");

        Specification s = filter(filter);
        System.out.println(s.toString());
    }
}
