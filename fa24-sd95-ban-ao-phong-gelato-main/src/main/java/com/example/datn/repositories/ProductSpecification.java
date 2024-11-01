package com.example.datn.repositories;



import com.example.datn.dto.Product.SearchProductDto;
import com.example.datn.entities.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification implements Specification<Product> {
    private SearchProductDto searchProductDto;

    public ProductSpecification(SearchProductDto searchRequest) {
        this.searchProductDto = searchRequest;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (searchProductDto.getMinPrice() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), searchProductDto.getMinPrice()));
        }

        if (searchProductDto.getMaxPrice() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), searchProductDto.getMaxPrice()));
        }

        if (searchProductDto.getProductName() != null) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%"+searchProductDto.getProductName()+"%"));
        }

        if(searchProductDto.getCode() != null) {
            predicates.add(criteriaBuilder.like(root.get("code"), "%"+searchProductDto.getCode()+"%"));

        }

        if(searchProductDto.getKeyword() != null) {
            String keyword = searchProductDto.getKeyword();

            Predicate productNamePredicate = criteriaBuilder.like(root.get("name"), "%" + keyword + "%");
            Predicate productCodePredicate = criteriaBuilder.like(root.get("code"), "%" + keyword + "%");
            Predicate combinedPredicate = criteriaBuilder.or(productNamePredicate, productCodePredicate);

            predicates.add(combinedPredicate);
        }

        predicates.add(criteriaBuilder.equal(root.get("status"), 1));
        predicates.add(criteriaBuilder.equal(root.get("deleteFlag"), false));
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
