package com.example.datn.controllers.api;


import com.example.datn.dto.Material.MaterialDto;
import com.example.datn.services.MaterialService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MaterialRestController {
    private final MaterialService materialService;

    public MaterialRestController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @PostMapping("/api/material")
    public MaterialDto createMaterialApi(@RequestBody MaterialDto materialDto) {
        return materialService.createMaterialApi(materialDto);
    }
}
