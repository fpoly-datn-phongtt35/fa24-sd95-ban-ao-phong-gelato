package com.example.datn.controllers;


import com.example.datn.dto.ColorDto;
import com.example.datn.dto.MaterialDto;
import com.example.datn.entities.Color;
import com.example.datn.entities.Material;
import com.example.datn.services.MaterialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @GetMapping
    public String getMaterial(@RequestParam(name = "page", defaultValue = "1") Integer page, Model model){
        Page<Material> listMaterial = materialService.getMaterial(page);
        model.addAttribute("material", listMaterial.getContent());
        model.addAttribute("totalPage", listMaterial.getTotalPages());
        model.addAttribute("currentPage", page);

        return "material/material";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model, Material material){
        model.addAttribute("material", material);
        return "material/material-create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("material") MaterialDto materialDto, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "material/material-create";
        }
        materialService.create(materialDto);
        attributes.addFlashAttribute("message", "Thêm chất liệu thành công!");
        return "redirect:/material";
    }

    @GetMapping("/update")
    public String materialDetail(@RequestParam Integer id, Model model){
        model.addAttribute("material", materialService.detail(id));
        return "material/material-update";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("material") MaterialDto materialDto, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "material/material-update";
        }
        materialService.create(materialDto);
        attributes.addFlashAttribute("message", "Cập nhật chất liệu thành công!");
        return "redirect:/material";
    }
}
