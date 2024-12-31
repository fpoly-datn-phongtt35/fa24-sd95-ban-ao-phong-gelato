package com.example.datn.services.impls;

import com.example.datn.dto.MaterialDto;
import com.example.datn.entities.Material;
import com.example.datn.repositories.MaterialRepository;
import com.example.datn.services.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialRepository materialRepository;


    @Override
    public Page<Material> getMaterial(Integer page) {
        int pageSize = 5;
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page-1, pageSize, sort);
        return materialRepository.findAll(pageable);
    }

    @Override
    public Material create(MaterialDto materialDto) {
        Material material = new Material();
        material.setId(materialDto.getId());
        material.setCode(materialDto.getCode());
        material.setName(materialDto.getName());
        material.setStatus(1);
        material.setDelete_flag(null);
        return materialRepository.save(material);
    }

    @Override
    public Material detail(Integer id) {
        return materialRepository.findById(id).orElse(null);
    }

    @Override
    public Material update(MaterialDto materialDto) {
        Material material = new Material();
        material.setId(materialDto.getId());
        material.setCode(materialDto.getCode());
        material.setName(materialDto.getName());
        material.setStatus(1);
        material.setDelete_flag(null);
        return materialRepository.save(material);
    }
}
