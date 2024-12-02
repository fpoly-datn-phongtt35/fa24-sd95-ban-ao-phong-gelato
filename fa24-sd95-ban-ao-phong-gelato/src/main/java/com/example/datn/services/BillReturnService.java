package com.example.datn.services;
import com.example.datn.dto.BillReturn.BillReturnCreateDto;
import com.example.datn.dto.BillReturn.BillReturnDetailDto;
import com.example.datn.dto.BillReturn.BillReturnDto;
import com.example.datn.dto.BillReturn.SearchBillReturnDto;

import java.util.List;
public interface BillReturnService {
    List<BillReturnDto> getAllBillReturns(SearchBillReturnDto searchBillReturnDto);

    BillReturnDto createBillReturn(BillReturnCreateDto billReturnCreateDto);

    BillReturnDetailDto getBillReturnDetailById(Long id);
    BillReturnDetailDto getBillReturnDetailByCode(String code);

    String generateHtmlContent(Long billReturnId);

    BillReturnDto updateStatus(Long id, int returnStatus);
}
