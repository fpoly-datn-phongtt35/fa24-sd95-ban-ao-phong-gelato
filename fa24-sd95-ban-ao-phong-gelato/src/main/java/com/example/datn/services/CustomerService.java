package com.example.datn.services;


import com.example.datn.dto.CustomerDto.CustomerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CustomerService {
    Page<CustomerDto> getAllCustomers(Pageable pageable);

    CustomerDto createCustomerAdmin(CustomerDto customerDto);

    Page<CustomerDto> searchCustomerAdmin(String keyword, Pageable pageable);
}
