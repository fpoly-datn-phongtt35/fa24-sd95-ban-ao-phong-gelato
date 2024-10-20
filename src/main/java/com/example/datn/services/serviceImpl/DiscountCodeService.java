package com.example.datn.services.serviceImpl;


import com.example.datn.dto.DiscountCode.DiscountCodeDto;
import com.example.datn.dto.DiscountCode.SearchDiscountCodeDto;
import com.example.datn.entities.DiscountCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface DiscountCodeService {
    Page<DiscountCodeDto> getAllDiscountCode(SearchDiscountCodeDto searchDiscountCodeDto, Pageable pageable);
    DiscountCodeDto saveDiscountCode(DiscountCodeDto discountCodeDto);
    DiscountCodeDto updateDiscountCode(DiscountCodeDto discountCodeDto);

    DiscountCodeDto getDiscountCodeById(Long id);
    DiscountCodeDto getDiscountCodeByCode(Long code);
    DiscountCodeDto updateStatus(Long discountCodeId, int status);
    Page<DiscountCodeDto> getAllAvailableDiscountCode(Pageable pageable);

    Page<DiscountCode> getDiscountCode(Integer page);
}
