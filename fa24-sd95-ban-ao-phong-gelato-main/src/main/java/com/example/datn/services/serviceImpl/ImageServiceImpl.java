package com.example.datn.services.serviceImpl;


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
    public List<Image> getAllImagesByProductId(Long productId)  {
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()) {
            return imageRepository.findAllByProduct(product.get());
        }
        return null;
    }

    @Override
    public void removeImageByIds(List<Long> ids) {
        imageRepository.deleteAllById(ids);
    }

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElse(null);
    }
//    @Override
//    public List<Image> saveALL(List<Image> images) {
//        return imageRepository.saveAll(images);
//    }
//
//    @Override
//    public Image save(Image images) {
//        return imageRepository.save(images);
//    }
//
//    @Override
//    public Image findByID(Long id) {
//        return imageRepository.findImageById(id);
//    }
//    @Override
//    public void delete(Long id) {
//        imageRepository.deleteById(id);
//    }
}
