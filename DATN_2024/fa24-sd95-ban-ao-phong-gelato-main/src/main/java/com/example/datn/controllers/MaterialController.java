package com.example.datn.controllers;

import com.example.datn.dto.MaterialDto;
import com.example.datn.entities.Material;
import com.example.datn.services.MaterialService;
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
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @GetMapping("/material-all")
    public String getMaterial(@RequestParam(name = "page", defaultValue = "1") Integer page, Model model) {
        Page<Material> listMaterial = materialService.getMaterial(page);
        model.addAttribute("material", listMaterial.getContent());
        model.addAttribute("totalPage", listMaterial.getTotalPages());
        model.addAttribute("currentPage", page);

        return "admin/material/material";
    }

    @GetMapping("/material-create")
    public String showCreateForm(Model model, Material material) {
        model.addAttribute("material", material);
        return "admin/material/material-create";
    }

    @PostMapping("/material-create")
    public String create(@Valid @ModelAttribute("material") MaterialDto materialDto, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "admin/material/material-create";
        }
        materialService.create(materialDto);
        attributes.addFlashAttribute("message", "Thêm chất liệu thành công!");
        return "redirect:/admin/material-all";
    }

    @GetMapping("/material-update")
    public String materialDetail(@RequestParam Integer id, Model model) {
        model.addAttribute("material", materialService.detail(id));
        return "admin/material/material-update";
    }

    @PostMapping("/material-update")
    public String update(@Valid @ModelAttribute("material") MaterialDto materialDto, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "admin/material/material-update";
        }
        materialService.update(materialDto);
        attributes.addFlashAttribute("message", "Cập nhật chất liệu thành công!");
        return "redirect:/admin/material-all";
    }
}
