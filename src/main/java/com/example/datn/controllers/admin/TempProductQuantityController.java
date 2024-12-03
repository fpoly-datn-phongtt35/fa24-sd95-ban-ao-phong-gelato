package com.example.datn.controllers.admin;

import com.example.datn.dto.Bill.ProductQuantityTempDto;
import com.example.datn.entities.TempProductQuantity;
import com.example.datn.repositories.TempProductQuantityRepository;
import com.example.datn.services.serviceImpl.TempProductQuantityService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/temp-product-quantity")
@RequiredArgsConstructor
public class TempProductQuantityController {

    private final TempProductQuantityRepository tempProductQuantityRepository;
    private final TempProductQuantityService tempProductQuantityService;


    @DeleteMapping("")
    public ResponseEntity<?> delete(@RequestParam Long create_id, @RequestParam(required = false) Optional<Long> product_detail_id) {
        if(product_detail_id.isPresent()) {
            tempProductQuantityService.delete(create_id,product_detail_id.get());
        }else{
            tempProductQuantityService.delete(create_id);
        }
        return ResponseEntity.ok().build();
    }
    @PostMapping("")
    @Transactional
    public ResponseEntity<?> update(@Valid @RequestBody List<ProductQuantityTempDto> productQuantityTempDtos, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        productQuantityTempDtos.forEach(req -> {
            req.getProductDetail().forEach(productDetail -> {
                TempProductQuantity tempProductQuantity = new TempProductQuantity();
                tempProductQuantity.setId(new TempProductQuantity.TempProductQuantityId(req.getCreateId(),productDetail.getProductDetailId()));
                tempProductQuantity.setQuantity(productDetail.getQuantity());
                tempProductQuantityService.saveOrUpdate(tempProductQuantity);
                System.out.println("save"+tempProductQuantity);
            });
        });
//
        return null;

    }

}
