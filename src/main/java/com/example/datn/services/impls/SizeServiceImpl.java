package com.example.datn.services.impls;


import com.example.datn.dto.SizeDto;
import com.example.datn.entities.Size;
import com.example.datn.repositories.SizeRepository;
import com.example.datn.services.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizeServiceImpl implements SizeService {

    @Autowired
    private SizeRepository sizeRepository;


    @Override
    public Page<Size> getSize(Integer page) {
        int pageSize = 5;
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page-1, pageSize, sort);
        return sizeRepository.findAll(pageable);
    }

    @Override
    public Size create(SizeDto sizeDto) {
        Size size = new Size();
        size.setId(sizeDto.getId());
        size.setCode(sizeDto.getCode());
        size.setName(sizeDto.getName());
        size.setDelete_flag(null);
        return sizeRepository.save(size);
    }

    @Override
    public Size detail(Integer id) {
        return sizeRepository.findById(id).orElse(null);
    }

    @Override
    public Size update(SizeDto sizeDto) {
        Size size = new Size();
        size.setId(sizeDto.getId());
        size.setCode(sizeDto.getCode());
        size.setName(sizeDto.getName());
        size.setDelete_flag(null);
        return sizeRepository.save(size);
    }
}
