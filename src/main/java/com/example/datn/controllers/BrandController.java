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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    public String getBrand(@RequestParam(name = "page", defaultValue = "1") Integer page, Model model){
        Page<Brand> listBrand = brandService.getBrand(page);
        model.addAttribute("brand", listBrand.getContent());
        model.addAttribute("totalPage", listBrand.getTotalPages());
        model.addAttribute("currentPage", page);
        return "brand/brand";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model, Brand brand){
        model.addAttribute("brand", brand);
        return "brand/brand-create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("brand") BrandDto brand, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "brand/brand-create";
        }
        brandService.create(brand);
        attributes.addFlashAttribute("message", "Thêm thương hiệu thành công!");
        return "redirect:/brand";
    }

    @GetMapping("/update")
    public String sizeDetail(@RequestParam Integer id, Model model){
        model.addAttribute("brand", brandService.detail(id));
        return "brand/brand-update";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("brand") BrandDto brand, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "brand/brand-update";
        }
        brandService.update(brand);
        attributes.addFlashAttribute("message", "Cập nhật thương hiệu thành công!");
        return "redirect:/brand";
    }
}
