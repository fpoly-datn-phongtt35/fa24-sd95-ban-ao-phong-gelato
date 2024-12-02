package com.example.datn.services;

import com.example.datn.entities.Image;

import java.util.List;

public interface ImageService {

    List<Image> getImage(Integer productId);

    void deleteImageById(List<Integer> ids);
}
