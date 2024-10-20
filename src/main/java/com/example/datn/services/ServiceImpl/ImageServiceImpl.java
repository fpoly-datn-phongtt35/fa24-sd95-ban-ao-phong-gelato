package com.example.datn.services.ServiceImpl;

import com.example.datn.entities.Image;
import com.example.datn.entities.Product;
import com.example.datn.repositories.ImageRepo;
import com.example.datn.repositories.ProductRepo;
import com.example.datn.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepo imageRepo;

    @Autowired
    private ProductRepo productRepo;

    @Override
    public List<Image> getAllImagesByProductId(Long productId)  {
        Optional<Product> product = productRepo.findById(productId);
        if(product.isPresent()) {
            return imageRepo.findAllByProduct(product.get());
        }
        return null;
    }

    @Override
    public void removeImageByIds(List<Long> ids) {
        imageRepo.deleteAllById(ids);
    }
}
