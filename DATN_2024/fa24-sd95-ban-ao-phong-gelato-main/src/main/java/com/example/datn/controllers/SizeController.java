package com.example.datn.controllers;

import com.example.datn.dto.SizeDto;
import com.example.datn.entities.Size;
import com.example.datn.services.SizeService;
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
public class SizeController {

    @Autowired
    private SizeService sizeService;

    @GetMapping("/size-all")
    public String getSize(@RequestParam(name = "page", defaultValue = "1") Integer page, Model model){
        Page<Size> listSize = sizeService.getSize(page);
        model.addAttribute("size", listSize.getContent());
        model.addAttribute("totalPage", listSize.getTotalPages());
        model.addAttribute("currentPage", page);
        return "admin/size/size";
    }

    @GetMapping("/size-create")
    public String showCreateForm(Model model, Size size){
        model.addAttribute("size", size);
        return "admin/size/size-create";
    }

    @PostMapping("/size-create")
    public String create(@Valid @ModelAttribute("size") SizeDto size, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "admin/size/size-create";
        }
        sizeService.create(size);
        attributes.addFlashAttribute("message", "Thêm kích cỡ thành công!");
        return "redirect:/admin/size-all";
    }

    @GetMapping("/size-update")
    public String sizeDetail(@RequestParam Integer id, Model model){
        model.addAttribute("size", sizeService.detail(id));
        return "admin/size/size-update";
    }


    @PostMapping("/size-update")
    public String update(@Valid @ModelAttribute("size") SizeDto size, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "admin/size/size-update";
        }
        sizeService.update(size);
        attributes.addFlashAttribute("message", "Cập nhật kích cỡ thành công!");
        return "redirect:/admin/size-all";
    }
}
