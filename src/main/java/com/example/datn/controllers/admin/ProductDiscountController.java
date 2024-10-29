package com.example.datn.controllers.admin;


import com.example.datn.dto.ProductDiscount.ProductDiscountCreateDto;
import com.example.datn.dto.ProductDiscount.ProductDiscountDto;
import com.example.datn.entities.Product;
import com.example.datn.entities.ProductDiscount;
import com.example.datn.repositories.ProductDetailRepository;
import com.example.datn.repositories.ProductDiscountRepository;
import com.example.datn.repositories.ProductRepository;
import com.example.datn.services.ProductDiscountService;
import com.example.datn.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
public class ProductDiscountController {
    private final ProductService productService;
    private final ProductDiscountService productDiscountService;
    private final ProductDetailRepository productDetailRepository;
    private final ProductDiscountRepository productDiscountRepository;

    @Autowired
    private ProductRepository productRepository;

    public ProductDiscountController(ProductService productService, ProductRepository productRepository, ProductDiscountService productDiscountService, ProductDetailRepository productDetailRepository, ProductDiscountRepository productDiscountRepository) {
        this.productService = productService;
        this.productDiscountService = productDiscountService;
        this.productDetailRepository = productDetailRepository;
        this.productDiscountRepository = productDiscountRepository;
    }

    @GetMapping("/admin-only/product-discount")
    public String viewProductDiscountPage(Model model, Pageable pageable) {
        List<ProductDiscount> productDiscountList = productDiscountRepository.findAll();
        model.addAttribute("productDiscounts", productDiscountList);
        return "/admin/product-discount";
    }

    @GetMapping("/admin-only/product-discount-create")
    public String viewProductDiscountCreatePage(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "/admin/product-discount-create";
    }

    @ResponseBody
    @PostMapping("/api/private/product-discount/multiple")
    public List<ProductDiscountDto> createProductDiscountMultiple(@Valid @RequestBody ProductDiscountCreateDto productDiscountCreateDto) {
        return productDiscountService.createProductDiscountMultiple(productDiscountCreateDto);
    }

    @ResponseBody
    @PostMapping("/api/private/product-discount/{id}/status/{status}")
    public ProductDiscountDto updateProductDiscount(@Valid @PathVariable Long id, @PathVariable boolean status) {
        return productDiscountService.updateCloseProductDiscount(id, status);
    }
}
