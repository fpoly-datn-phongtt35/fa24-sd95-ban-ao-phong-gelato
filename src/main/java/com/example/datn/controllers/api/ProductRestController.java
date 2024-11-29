package com.example.datn.controllers.api;

import com.example.datn.dto.Product.ProductDto;
import com.example.datn.dto.Product.SearchProductDto;
import com.example.datn.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductRestController {
    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/api/products")
    public Page<ProductDto> getAllProductsApi(@PageableDefault(page = 0, size = 12) Pageable pageable) {
        return productService.getAllProductApi(pageable);
    }

//    @CrossOrigin(origins = "http://localhost:3000") // Thay thế bằng domain của bạn
    @GetMapping("/api/products-no-pagination")
    public List<ProductDto> getAllProductsApi(SearchProductDto searchRequest) {
        if (searchRequest == null) {
            searchRequest = new SearchProductDto();
        }
//        System.out.println("Search request received: " + searchRequest); // Log để kiểm tra
        return productService.getAllProductNoPaginationApi(searchRequest);
    }

    @GetMapping("/api/products/filter")
    public Page<ProductDto> filterProductApi(SearchProductDto searchRequest, @PageableDefault(page = 0, size = 10) Pageable page){
        return productService.searchProduct(searchRequest, page);
    }

    @GetMapping("/api/products/getByBarcode")
    public ProductDto filterProductApi(@RequestParam String barcode){
        return productService.getProductByBarcode(barcode);
    }

    @GetMapping("/api/products/{detailId}/productDetail")
    public ProductDto getByProductDetailId(@PathVariable Long detailId) {
        return productService.getByProductDetailId(detailId);
    }
}
