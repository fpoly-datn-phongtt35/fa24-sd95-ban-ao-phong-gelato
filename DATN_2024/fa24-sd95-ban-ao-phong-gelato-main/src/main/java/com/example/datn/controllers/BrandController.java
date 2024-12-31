package com.example.datn.controllers;

import com.example.datn.dto.BrandDto;
import com.example.datn.entities.Brand;
import com.example.datn.services.BrandService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class BrandController {

    @Autowired
    private BrandService brandService;

    // Lấy danh sách thương hiệu với đường dẫn /admin/brand-list
    @GetMapping("/brand-all")
    public String getBrand(@RequestParam(name = "page", defaultValue = "1") Integer page, Model model){
        Page<Brand> listBrand = brandService.getBrand(page);
        model.addAttribute("brand", listBrand.getContent());
        model.addAttribute("totalPage", listBrand.getTotalPages());
        model.addAttribute("currentPage", page);
        return "admin/brand/brand"; // Đảm bảo rằng bạn có tệp brand.html trong thư mục admin/brand
    }

    // Hiển thị form tạo mới thương hiệu với đường dẫn /admin/brand-create
    @GetMapping("/brand-create")
    public String showCreateForm(Model model, Brand brand){
        model.addAttribute("brand", brand);
        return "admin/brand/brand-create"; // Đảm bảo rằng bạn có tệp brand-create.html trong thư mục admin/brand
    }

    // Xử lý yêu cầu tạo mới thương hiệu với đường dẫn /admin/brand-create
    @PostMapping("/brand-create")
    public String create(@Valid @ModelAttribute("brand") BrandDto brand, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "admin/brand/brand-create"; // Đảm bảo rằng bạn có tệp brand-create.html trong thư mục admin/brand
        }
        brandService.create(brand);
        attributes.addFlashAttribute("message", "Thêm thương hiệu thành công!");
        return "redirect:/admin/brand-all";
    }

    // Hiển thị form cập nhật thương hiệu với đường dẫn /admin/brand-update
    @GetMapping("/brand-update")
    public String sizeDetail(@RequestParam Integer id, Model model){
        model.addAttribute("brand", brandService.detail(id));
        return "admin/brand/brand-update"; // Đảm bảo rằng bạn có tệp brand-update.html trong thư mục admin/brand
    }

    // Xử lý yêu cầu cập nhật thương hiệu với đường dẫn /admin/brand-update
    @PostMapping("/brand-update")
    public String update(@Valid @ModelAttribute("brand") BrandDto brand, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "admin/brand/brand-update"; // Đảm bảo rằng bạn có tệp brand-update.html trong thư mục admin/brand
        }
        brandService.update(brand);
        attributes.addFlashAttribute("message", "Cập nhật thương hiệu thành công!");
        return "redirect:/admin/brand-all";
    }
}
