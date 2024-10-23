package com.example.datn.controllers;


import com.example.datn.dto.ColorDto;
import com.example.datn.entities.Color;
import com.example.datn.services.ColorService;
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
public class ColorController {

    @Autowired
    private ColorService colorService;

    @GetMapping("/color-list")
    public String getColor(@RequestParam(name = "page", defaultValue = "1") Integer page, Model model){
        Page<Color> listColor = colorService.getColor(page);
        model.addAttribute("color", listColor.getContent());
        model.addAttribute("totalPage", listColor.getTotalPages());
        model.addAttribute("currentPage", page);

        return "admin/color/color";
    }

    @GetMapping("/color-create")
    public String showCreateForm(Model model, Color color){
        model.addAttribute("color", color);
        return "admin/color/color-create";
    }

    @PostMapping("/color-create")
    public String create(@Valid @ModelAttribute("color") ColorDto color, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "admin/color/color-create";
        }
        colorService.create(color);
        attributes.addFlashAttribute("message", "Thêm màu sắc thành công!");
        return "redirect:/admin/color-list";
    }

    @GetMapping("/color-update")
    public String sizeDetail(@RequestParam Integer id, Model model){
        model.addAttribute("color", colorService.detail(id));
        return "admin/color/color-update";
    }

    @PostMapping("/color-update")
    public String update(@Valid @ModelAttribute("color") ColorDto color, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "admin/color/color-update";
        }
        colorService.update(color);
        attributes.addFlashAttribute("message", "Cập nhật màu sắc thành công!");
        return "redirect:/admin/color-list";
    }
}
