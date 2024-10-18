package com.example.demo.controller.admin;

import com.example.demo.dto.DiscountProduct.DiscountProductCreateDto;
import com.example.demo.dto.DiscountProduct.DiscountProductDto;
import com.example.demo.entity.Category;
import com.example.demo.entity.DiscountProduct;
import com.example.demo.repository.ProductDetailRepo;
import com.example.demo.repository.ProductDiscountRepo;
import com.example.demo.repository.ProductRepo;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductDiscountService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/discounts")
public class DiscountProductController {

    private final ProductService productService;
    private final ProductDiscountService productDiscountService;
    private final CategoryService categoryService;
    private final ProductDetailRepo productDetailRepo;
    private final ProductDiscountRepo productDiscountRepo;

    public DiscountProductController(ProductService productService, ProductDiscountService productDiscountService,
                                     CategoryService categoryService, ProductDetailRepo productDetailRepo,
                                     ProductDiscountRepo productDiscountRepo) {
        this.productService = productService;
        this.productDiscountService = productDiscountService;
        this.categoryService = categoryService;
        this.productDetailRepo = productDetailRepo;
        this.productDiscountRepo = productDiscountRepo;
    }

    @GetMapping("/list")
    public String viewProductDiscountPage(Model model, Pageable pageable) {
        Page<DiscountProduct> discountProductPage = productDiscountRepo.findAllByProductDetailNotNull(pageable);
        model.addAttribute("discountProducts", discountProductPage.getContent());
        model.addAttribute("page", discountProductPage);
        return "Discount/discountProduct";
    }

    @GetMapping("/create")
    public String viewProductDiscountCreatePage(Model model) {
        List<Category> categories = categoryService.getAll();
        model.addAttribute("categories", categories);
        return "Discount/createDiscountProduct";
    }

    @ResponseBody
    @PostMapping("/api/admin/discounts/multiple") //Phương thức thêm nhiều sản phẩm giảm giá:
    public List<DiscountProductDto> createProductDiscountMultiple(@Valid @RequestBody DiscountProductCreateDto discountProductCreateDto) {
        return productDiscountService.createProductDiscountMultiple(discountProductCreateDto);
    }

    @ResponseBody
    @PostMapping("/api/admin/discounts/{id}/status/{status}") //Phương thức cập nhật trạng thái sản phẩm giảm giá
    public DiscountProductDto updateProductDiscount(@Valid @PathVariable Long id, @PathVariable boolean status) {
        return productDiscountService.updateCloseProductDiscount(id, status);
    }

    @ResponseBody
    @GetMapping("/api/admin/discounts") // Phương thức mới để lấy tất cả sản phẩm giảm giá
    public List<DiscountProductDto> getAllDiscountProducts() {
        List<DiscountProduct> discountProducts = productDiscountService.getAllProductDiscount();
        return discountProducts.stream()
                .map(this::convertToDto) // Chuyển đổi sang DTO nếu cần
                .collect(Collectors.toList());
    }

    private DiscountProductDto convertToDto(DiscountProduct discountProduct) {
        DiscountProductDto discountProductDto = new DiscountProductDto();
        discountProductDto.setId(discountProduct.getId());
        discountProductDto.setDiscountedAmount(discountProduct.getDiscountedAmount());
        discountProductDto.setClosed(discountProduct.isClosed());
        discountProductDto.setStartDate(discountProduct.getStartDate());
        discountProductDto.setEndDate(discountProduct.getEndDate());

        // Kiểm tra xem ProductDetail có phải là null không
        if (discountProduct.getProductDetail() != null) {
            discountProductDto.setProductDetailId(discountProduct.getProductDetail().getId());
        } else {
            discountProductDto.setProductDetailId(null); // Hoặc có thể set một giá trị mặc định
        }

        return discountProductDto;
    }

}

//    // Hiển thị danh sách các đợt giảm giá
//    @GetMapping
//    public String getAllDiscountProducts(Model model) {
//        List<DiscountProduct> discountProducts = productDiscountService.getAllDiscountProducts();
////        System.out.println(discountProducts); // In ra danh sách để kiểm tra
//        model.addAttribute("discountProducts", discountProducts);
//        return "/Discount/discountProducts"; // Tên file HTML sẽ hiển thị danh sách
//    }
//
//    // Hiển thị chi tiết đợt giảm giá theo ID
//    @GetMapping("/{id}")
//    public String getDiscountProductById(@PathVariable Long id, Model model) {
//        DiscountProduct discountProduct = productDiscountService.getDiscountProductById(id);
//        model.addAttribute("discountProduct", discountProduct);
//        return "discountProductDetail"; // Tên file HTML để hiển thị chi tiết đợt giảm giá
//    }
////
////    // Thêm đợt giảm giá
//    @GetMapping("/create")
//    public String createDiscountProductForm(Model model) {
//        model.addAttribute("discountProduct", new DiscountProduct());
//        return "createDiscountProduct"; // Tên file HTML để hiển thị form thêm
//    }
////
////    @PostMapping("/create")
////    public String createDiscountProduct(@ModelAttribute DiscountProduct discountProduct) {
////        productDiscountService.createDiscountProduct(discountProduct);
////        return "redirect:/discounts"; // Chuyển hướng về danh sách sau khi thêm
////    }
////
////    // Cập nhật đợt giảm giá
//    @GetMapping("/edit/{id}")
//    public String updateDiscountProductForm(@PathVariable Long id, Model model) {
//        DiscountProduct discountProduct = productDiscountService.getDiscountProductById(id);
//        model.addAttribute("discountProduct", discountProduct);
//        return "editDiscountProduct"; // Tên file HTML để hiển thị form sửa
//    }
////
//    @PostMapping("/{id}")
//    public String updateDiscountProduct(@PathVariable Long id, @ModelAttribute DiscountProduct discountProduct, RedirectAttributes attributes) {
//        productDiscountService.updateDiscountProduct(id, discountProduct);
//        attributes.addFlashAttribute("message", "Cập nhật mã giảm giá thành công !");
//        return "redirect:/discounts"; // Chuyển hướng về danh sách sau khi cập nhật
//    }
////
////    /// Đóng đợt giảm giá (thay vì xóa)
//    @PostMapping("/{id}/close") // Thay đổi đường dẫn để tránh xung đột
//    public String closeDiscountProduct(@PathVariable Long id) {
//        // Thay đổi trạng thái closed thành true
//        productDiscountService.closeDiscountProduct(id);
//        return "redirect:/discounts"; // Chuyển hướng về danh sách sau khi đóng
//    }
//
//    @PostMapping("/create")
//    public String add(@ModelAttribute DiscountProduct discountProduct, RedirectAttributes redirectAttributes){
//        productDiscountRepo.save(discountProduct);
//        redirectAttributes.addFlashAttribute("message", "Tạo mã giảm giá mới thành công");
//        return "redirect:/discounts";
//    }

