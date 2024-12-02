package com.example.datn.services;


import com.example.datn.dto.AddressShipping.AddressShippingDto;
import com.example.datn.dto.AddressShipping.AddressShippingDtoAdmin;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressShippingService {
    List<AddressShippingDto> getAddressShippingByAccountId();
    AddressShippingDto saveAddressShippingUser(AddressShippingDto addressShippingDto);

    AddressShippingDto saveAddressShippingAdmin(AddressShippingDtoAdmin addressShippingDto);

    void deleteAddressShipping(Long id);
}
