package com.example.datn.services;

import com.example.datn.dto.SizeDto;
import com.example.datn.entities.Size;
import org.springframework.data.domain.Page;

public interface SizeService {

    Page<Size> getSize(Integer pageSize);

    Size create(SizeDto sizeDto);

    Size detail(Integer id);

    Size update(SizeDto sizeDto);
}
