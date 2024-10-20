package com.example.datn.services;

import com.example.datn.entities.Image;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ImageService {
    List<Image> getAllImagesByProductId(Long productId);
    void removeImageByIds(List<Long> ids);
}
