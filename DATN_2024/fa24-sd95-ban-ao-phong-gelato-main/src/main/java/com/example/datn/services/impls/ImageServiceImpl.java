package com.example.datn.services.impls;


import com.example.datn.entities.Image;
import com.example.datn.entities.Product;
import com.example.datn.repositories.ImageRepository;
import com.example.datn.repositories.ProductRepository;
import com.example.datn.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductRepository productRepository;


    @Override
    public List<Image> getImage(Integer productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()){
            return imageRepository.findByProduct(product.get());
        }
        return null;
    }

    @Override
    public void deleteImageById(List<Integer> ids) {
        imageRepository.deleteAllById(ids);
    }
}
