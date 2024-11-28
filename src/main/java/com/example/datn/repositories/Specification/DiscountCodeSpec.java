package com.example.datn.repositories.Specification;

import com.example.datn.dto.DiscountCode.SearchDiscountCodeDto;
import com.example.datn.entities.DiscountCode;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class DiscountCodeSpec implements Specification<DiscountCode> {
    private SearchDiscountCodeDto searchDiscountCodeDto;

    public DiscountCodeSpec(SearchDiscountCodeDto searchRequest) {
        this.searchDiscountCodeDto = searchRequest;
    }

    @Override
    public Predicate toPredicate(Root<DiscountCode> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (searchDiscountCodeDto.getKeyword() != null && !searchDiscountCodeDto.getKeyword().trim().isEmpty()) {
            String keyword = searchDiscountCodeDto.getKeyword().trim().toLowerCase();
            Predicate codePredicate = cb.like(cb.lower(root.get("code")), "%" + keyword + "%");
            Predicate detailPredicate = cb.like(cb.lower(root.get("detail")), "%" + keyword + "%");
            predicates.add(cb.or(codePredicate, detailPredicate));
        }

        if (searchDiscountCodeDto.getCode() != null && !searchDiscountCodeDto.getCode().trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("code")), "%" + searchDiscountCodeDto.getCode().trim().toLowerCase() + "%"));
        }

        if (searchDiscountCodeDto.getDetail() != null && !searchDiscountCodeDto.getDetail().trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("detail")), "%" + searchDiscountCodeDto.getDetail().trim().toLowerCase() + "%"));
        }

        if (searchDiscountCodeDto.getStartDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"), searchDiscountCodeDto.getStartDate()));
        }

        if (searchDiscountCodeDto.getEndDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("endDate"), searchDiscountCodeDto.getEndDate()));
        }

        if (searchDiscountCodeDto.getDiscountCodeType() != null) {
            predicates.add(cb.equal(root.get("type"), searchDiscountCodeDto.getDiscountCodeType()));
        }

        if (searchDiscountCodeDto.getMaximumUsage() != null) {
            predicates.add(cb.equal(root.get("maximumUsage"), searchDiscountCodeDto.getMaximumUsage()));
        }

        if (searchDiscountCodeDto.getStatus() != null) {
            predicates.add(cb.equal(root.get("status"), searchDiscountCodeDto.getStatus()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }


}
