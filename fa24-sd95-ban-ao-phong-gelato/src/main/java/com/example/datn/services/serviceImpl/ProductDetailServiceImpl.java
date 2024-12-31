package com.example.datn.services.serviceImpl;


import com.example.datn.dto.Product.ProductDetailDto;
import com.example.datn.entities.Product;
import com.example.datn.entities.ProductDetail;
import com.example.datn.entities.ProductDiscount;
import com.example.datn.exceptions.NotFoundException;
import com.example.datn.repositories.ProductDetailRepository;
import com.example.datn.repositories.ProductDiscountRepository;
import com.example.datn.repositories.ProductRepository;
import com.example.datn.services.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {
@Autowired
private ProductDetailRepository productDetailRepository;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductDiscountRepository productDiscountRepository;

    @Override
    public ProductDetail save(ProductDetail productDetail) {
        if (productDetail.getId() == null){
            return productDetailRepository.save(productDetail);
        }else{
            ProductDetail productDetail1 = productDetailRepository.getOne(productDetail.getId());
            int i = productDetail.getQuantity();
            int b = productDetail1.getQuantity();
            productDetail.setQuantity(b - i);
            return productDetailRepository.save(productDetail);
        }

    }


    @Override
    public ProductDetail getProductDetailByProductCode(String code){
        Product product = productRepository.findByCode(code);

        return productDetailRepository.getProductDetailByProduct(product);

    }

    @Override
    public List<ProductDetailDto> getByProductId(Long id) throws NotFoundException {
        Product product = productRepository.findById(id).orElseThrow( () -> new NotFoundException("Product not found"));
        List<ProductDetail> productDetails = productDetailRepository.getProductDetailByProductId(id);
        List<ProductDetailDto> productDetailDTOs = new ArrayList<>();

        for (ProductDetail productDetail : productDetails) {
            ProductDetailDto productDetailDTO = new ProductDetailDto();
            // Set properties of productDetailDTO based on productDetail
            productDetailDTO.setId(productDetail.getId());
            productDetailDTO.setProductId(productDetail.getProduct().getId());
            productDetailDTO.setPrice(productDetail.getPrice());
            productDetailDTO.setSize(productDetail.getSize());
            productDetailDTO.setColor(productDetail.getColor());
            productDetailDTO.setQuantity(productDetail.getQuantity());

            ProductDiscount productDiscount = productDiscountRepository.findValidDiscountByProductDetailId(productDetail.getId());
            if(productDiscount != null) {
//                Date endDate = productDiscount.getEndDate();
//                Date currentDate = new Date();
//                if (currentDate.compareTo(endDate) > 0) {
//                }
                productDetailDTO.setDiscountedPrice(productDiscount.getDiscountedAmount());

            }
            // Set other properties as needed
            productDetailDTOs.add(productDetailDTO);
        }
        return productDetailDTOs;
    }

}
