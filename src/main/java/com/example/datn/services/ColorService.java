package com.example.datn.services;

import com.example.datn.dto.ColorDto;
import com.example.datn.entities.Color;
import org.springframework.data.domain.Page;

public interface ColorService {

    Page<Color> getColor(Integer page);

    Color create(ColorDto colorDto);

    Color detail(Integer id);

    Color update(ColorDto colorDto);
}
