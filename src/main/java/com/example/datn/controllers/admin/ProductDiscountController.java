package com.example.datn.controllers.admin;


import com.example.datn.dto.ProductDiscount.ProductDiscountCreateDto;
import com.example.datn.dto.ProductDiscount.ProductDiscountDto;
import com.example.datn.entities.Color;
import com.example.datn.entities.Product;
import com.example.datn.entities.ProductDiscount;
import com.example.datn.entities.Size;
import com.example.datn.exceptions.NotFoundException;
import com.example.datn.repositories.*;
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
    private final ColorRepository colorRepository; // Khai báo ColorRepository
    private final SizeRepository sizeRepository; // Khai báo SizeRepository
    private final ProductRepository productRepository; // Khai báo ProductRepository

    // Constructor injection
    @Autowired
    public ProductDiscountController(ProductService productService,
                                     ProductRepository productRepository,
                                     ProductDiscountService productDiscountService,
                                     ProductDetailRepository productDetailRepository,
                                     ProductDiscountRepository productDiscountRepository,
                                     ColorRepository colorRepository,
                                     SizeRepository sizeRepository) { // Thêm ColorRepository vào constructor
        this.productService = productService;
        this.productDiscountService = productDiscountService;
        this.productDetailRepository = productDetailRepository;
        this.productDiscountRepository = productDiscountRepository;
        this.colorRepository = colorRepository; // Gán biến colorRepository
        this.productRepository = productRepository; // Gán biến productRepository
        this.sizeRepository = sizeRepository;
    }

    @GetMapping("/admin-only/product-discount")
    public String viewProductDiscountPage(Model model, @RequestParam(required = false, name = "color") String color,
                                          @RequestParam(required = false, name = "size") String size) {
        // Lấy danh sách các màu sắc và kích cỡ từ repository
        List<Color> colors = colorRepository.findAll();
        List<Size> sizes = sizeRepository.findAll();

        // Thêm danh sách vào model để hiển thị trong giao diện
        model.addAttribute("colors", colors);
        model.addAttribute("sizes", sizes);

        // Thêm giá trị lọc hiện tại vào model để hiển thị lại trên giao diện
        model.addAttribute("selectedColor", color);
        model.addAttribute("selectedSize", size);

        // Lấy danh sách các màu sắc và kích cỡ từ repository
        List<Color> colors = colorRepository.findAll();
        List<Size> sizes = sizeRepository.findAll();

        // Thêm danh sách vào model để hiển thị trong giao diện
        model.addAttribute("colors", colors);
        model.addAttribute("sizes", sizes);

        // Thêm giá trị lọc hiện tại vào model để hiển thị lại trên giao diện
        model.addAttribute("selectedColor", color);
        model.addAttribute("selectedSize", size);

        Color colorEntity = null;
        Size sizeEntity = null;

        if (color != null && !color.isEmpty()) {
            colorEntity = colorRepository.findByName(color)
                    .orElseThrow(() -> new NotFoundException("Color not found"));
        }

        if (size != null && !size.isEmpty()) {
            sizeEntity = sizeRepository.findByName(size)
                    .orElseThrow(() -> new NotFoundException("Size not found"));
        }

        List<ProductDiscount> productDiscountList = productDiscountService.getAllProductDiscount(colorEntity, sizeEntity);
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

    //Thêm @RequestParam cho color và size: Phương thức viewProductDiscountPage nhận các tham số color và size từ request để truyền xuống service.
    //Gọi service để lấy dữ liệu: Gọi productDiscountService.getAllProductDiscount(color, size) để lấy danh sách giảm giá dựa trên bộ lọc màu sắc và kích cỡ.
}
