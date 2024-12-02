package com.example.datn.services.impls;


import com.example.datn.dto.BrandDto;
import com.example.datn.entities.Brand;
import com.example.datn.repositories.BrandRepository;
import com.example.datn.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;


    @Override
    public Page<Brand> getBrand(Integer page) {
        int pageSize = 5;
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page-1, pageSize, sort);
        return brandRepository.findAll(pageable);
    }

    @Override
    public Brand create(BrandDto brandDto) {
        Brand brand = new Brand();
        brand.setId(brandDto.getId());
        brand.setCode(brandDto.getCode());
        brand.setName(brandDto.getName());
        brand.setStatus(1);
        brand.setDelete_flag(null);
        return brandRepository.save(brand);
    }

    @Override
    public Brand detail(Integer id) {
        return brandRepository.findById(id).orElse(null);
    }

    @Override
    public Brand update(BrandDto brandDto) {
        Brand brand = new Brand();
        brand.setId(brandDto.getId());
        brand.setCode(brandDto.getCode());
        brand.setName(brandDto.getName());
        brand.setStatus(1);
        brand.setDelete_flag(null);
        return brandRepository.save(brand);
    }
}
