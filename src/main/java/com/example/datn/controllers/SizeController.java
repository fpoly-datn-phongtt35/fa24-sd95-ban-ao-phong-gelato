package com.example.datn.controllers;


import com.example.datn.dto.SizeDto;
import com.example.datn.entities.Size;
import com.example.datn.services.SizeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.*;

@Controller
@RequestMapping("/size")
public class SizeController {

    @Autowired
    private SizeService sizeService;

    @GetMapping
    public String index(@RequestParam(name = "page", defaultValue = "1") Integer page, Model model){
        Page<Size> listSize = sizeService.getSize(page);
        model.addAttribute("size", listSize.getContent());
        model.addAttribute("totalPage", listSize.getTotalPages());
        model.addAttribute("currentPage", page);

        return "size/size";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model, Size size){
        model.addAttribute("size", size);
        return "size/size-create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("size") SizeDto size, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "size/size-create";
        }
        sizeService.create(size);
        attributes.addFlashAttribute("message", "Thêm kích cỡ thành công!");
        return "redirect:/size";
    }


    @GetMapping("/update")
    public String sizeDetail(@RequestParam Integer id, Model model){
        model.addAttribute("size", sizeService.detail(id));
        return "size/size-update";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("size") SizeDto size, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "size/size-update";
        }
        sizeService.create(size);
        attributes.addFlashAttribute("message", "Cập nhật kích cỡ thành công!");
        return "redirect:/size";
    }
}
