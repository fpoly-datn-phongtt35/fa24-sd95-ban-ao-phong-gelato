package com.example.datn.services.ServiceImpl;

import com.example.datn.dto.DiscountProduct.DiscountProductCreateDto;
import com.example.datn.dto.DiscountProduct.DiscountProductDto;
import com.example.datn.entities.DiscountProduct;
import com.example.datn.entities.ProductDetail;
import com.example.datn.exceptions.NotFoundException;
import com.example.datn.repositories.ProductDetailRepo;
import com.example.datn.repositories.ProductDiscountRepo;
import com.example.datn.services.ProductDiscountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductDiscountServiceImpl implements ProductDiscountService {
    private final ProductDiscountRepo productDiscountRepo;

    private final ProductDetailRepo productDetailRepo;

    public ProductDiscountServiceImpl(ProductDetailRepo productDetailRepo,ProductDiscountRepo productDiscountRepo) {
        this.productDiscountRepo = productDiscountRepo;
        this.productDetailRepo = productDetailRepo;
    }


    @Override
    public List<DiscountProduct> getAllProductDiscount(){
        return productDiscountRepo.findAll();

    }

    @Override
    public DiscountProductDto updateCloseProductDiscount(Long discountId, boolean closed) {
//        DiscountProduct discountProduct = productDiscountRepo.findById(discountId).orElseThrow(() -> new NotFoundException("Product discount not found"));
//        discountProduct.setClosed(closed);
//        return convertToDto(productDiscountRepo.save(discountProduct));
        DiscountProduct discountProduct = productDiscountRepo.findById(discountId)
                .orElseThrow(() -> new NotFoundException("Product discount not found"));
        discountProduct.setClosed(closed);
        return convertToDto(productDiscountRepo.save(discountProduct));
    }
    @Override
    public List<DiscountProductDto> createProductDiscountMultiple(DiscountProductCreateDto discountProductCreateDto) {
        List<DiscountProduct> discountProductList = discountProductCreateDto.getDiscountProducts().stream().map(productDiscountDto -> {
            DiscountProduct discountProduct = productDiscountRepo.findAllByProductDetail_Id(productDiscountDto.getProductDetailId());
            if(discountProduct != null) {
                productDiscountDto.setId(discountProduct.getId());
            }
            productDiscountDto.setClosed(false);
            return convertToEntity(productDiscountDto);
        }).collect(Collectors.toList());
        productDiscountRepo.saveAll(discountProductList);
        return discountProductCreateDto.getDiscountProducts();
    }

    private DiscountProduct convertToEntity(DiscountProductDto discountProductDto) {
        DiscountProduct discountProduct = new DiscountProduct();
        discountProduct.setId(discountProductDto.getId());
        discountProduct.setDiscountedAmount(discountProductDto.getDiscountedAmount());
        discountProduct.setClosed(discountProductDto.isClosed());
        discountProduct.setStartDate(discountProductDto.getStartDate());
        discountProduct.setEndDate(discountProductDto.getEndDate());
        ProductDetail productDetail = productDetailRepo.findById(discountProductDto.getProductDetailId()).orElseThrow(() -> new NotFoundException("Product detail id not found"));
        discountProduct.setProductDetail(productDetail);
        return discountProduct;
    }
    private DiscountProductDto convertToDto(DiscountProduct discountProduct) {
        DiscountProductDto discountProductDto = new DiscountProductDto();
        discountProductDto.setId(discountProduct.getId());
        discountProductDto.setDiscountedAmount(discountProduct.getDiscountedAmount());
        discountProductDto.setClosed(discountProduct.isClosed());
        discountProductDto.setStartDate(discountProduct.getStartDate());
        discountProductDto.setEndDate(discountProduct.getEndDate());
        discountProductDto.setProductDetailId(discountProduct.getProductDetail().getId());
        return discountProductDto;
    }
}
