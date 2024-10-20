package com.example.datn.repositories.Specification;

import com.example.datn.dto.Product.SearchProductDto;
import com.example.datn.entities.Product;
import com.example.datn.entities.ProductDetail;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecification implements Specification<Product> {
    private SearchProductDto searchProductDto;

    public ProductSpecification(SearchProductDto searchRequest){
        this.searchProductDto = searchRequest;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (searchProductDto.getMinPrice() != null) {
            // Tạo subquery để lấy giá của ProductDetail
            Subquery<Double> subquery = query.subquery(Double.class);
            Root<Product> productRoot = subquery.from(Product.class);
            Join<Product, ProductDetail> productDetailJoin = productRoot.join("productDetails"); // Chú ý: Tên trường phải đúng!
            subquery.select(criteriaBuilder.min(productDetailJoin.get("price")));
            subquery.where(criteriaBuilder.equal(productRoot.get("id"), root.get("id")));
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(subquery, searchProductDto.getMinPrice()));
        }

        if (searchProductDto.getProductName() != null) {
            predicates.add(criteriaBuilder.like(root.get("code"), "%" + searchProductDto.getProductName() + "%"));
        }

        if (searchProductDto.getCode() != null) {
            predicates.add(criteriaBuilder.like(root.get("code"), "%" + searchProductDto.getCode() + "%"));
        }

        if (searchProductDto.getKeyword() != null) {
            String keyword = searchProductDto.getKeyword();
            Predicate productNamePredicate = criteriaBuilder.like(root.get("name"), "%" + keyword + "%");
            Predicate productCodePredicate = criteriaBuilder.like(root.get("code"), "%" + keyword + "%");
            predicates.add(criteriaBuilder.or(productNamePredicate, productCodePredicate));
        }

        if (searchProductDto.getCategoryId() != null) {
            predicates.add(root.get("category").get("id").in(searchProductDto.getCategoryId()));
        }

        if (searchProductDto.getGender() != null) {
            predicates.add(criteriaBuilder.equal(root.get("gender"), searchProductDto.getGender()));
        }

        // Điều kiện status và deleteFlag
        predicates.add(criteriaBuilder.equal(root.get("status"), 1));
        predicates.add(criteriaBuilder.equal(root.get("deleteFlag"), false));

        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
