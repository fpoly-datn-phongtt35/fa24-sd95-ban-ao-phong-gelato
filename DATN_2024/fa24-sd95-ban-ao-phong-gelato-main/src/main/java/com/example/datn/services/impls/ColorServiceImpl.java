package com.example.datn.services.impls;


import com.example.datn.dto.ColorDto;
import com.example.datn.entities.Color;
import com.example.datn.repositories.ColorRepository;
import com.example.datn.services.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ColorServiceImpl implements ColorService {

    @Autowired
    private ColorRepository colorRepository;


    @Override
    public Page<Color> getColor(Integer page) {
        int pageSize = 5;
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page-1, pageSize, sort);
        return colorRepository.findAll(pageable);
    }

    @Override
    public Color create(ColorDto colorDto) {
        Color color = new Color();
        color.setId(colorDto.getId());
        color.setCode(colorDto.getCode());
        color.setName(colorDto.getName());
        color.setDelete_flag(null);
        return colorRepository.save(color);
    }

    @Override
    public Color detail(Integer id) {
        return colorRepository.findById(id).orElse(null);
    }

    @Override
    public Color update(ColorDto colorDto) {
        Color color = new Color();
        color.setId(colorDto.getId());
        color.setCode(colorDto.getCode());
        color.setName(colorDto.getName());
        color.setDelete_flag(null);
        return colorRepository.save(color);
    }
}
