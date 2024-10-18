package com.example.datn.services;

import com.example.datn.dto.MaterialDto;
import com.example.datn.entities.Material;
import org.springframework.data.domain.Page;

public interface MaterialService {

    Page<Material> getMaterial(Integer page);

    Material create(MaterialDto materialDto);

    Material detail(Integer id);

    Material update(MaterialDto materialDto);
}
