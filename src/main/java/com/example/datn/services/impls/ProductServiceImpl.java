package com.example.datn.services.impls;


import com.example.datn.dto.product.ProductDetailDto;
import com.example.datn.dto.product.ProductDto;
import com.example.datn.entities.Product;
import com.example.datn.entities.ProductDetail;
import com.example.datn.repositories.ProductDetailRepository;
import com.example.datn.repositories.ProductRepository;
import com.example.datn.services.ProductService;
import com.example.datn.utils.QRProductUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;


    @Override
    public Page<Product> getProduct(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product add(Product product) throws IOException {
        if (product.getCode().trim() == "" || product.getCode() == null){
            Product currentProduct = productRepository.findTopByOrderByIdDesc();
            Integer nextCode = Integer.valueOf(currentProduct.getCode() + 1);
            String productCode = "SP" + String.format("%04d", nextCode); //
            product.setCode(productCode);
        }

        Double minPrice = Double.valueOf(1000000000);
        for (ProductDetail productDetail : product.getProductDetail()){
            if (productDetail.getPrice() < minPrice){
                minPrice = productDetail.getPrice();
            }

            QRProductUtils.generateQRCode(productDetail.getBarcode(), productDetail.getBarcode());
        }

        product.setPrice(minPrice);
        product.setDelete_flag(false);
        product.setCreate_date(LocalDateTime.now().now());
        product.setUpdated_date(LocalDateTime.now());
        return productRepository.save(product);
    }

    private ProductDto convertToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setCode(product.getCode());
        productDto.setName(product.getName());
        productDto.setImageUrl(product.getImage().get(0).getLink());
        productDto.setDescribe(product.getDescribe());
        productDto.setCreate_date(product.getCreate_date());
        productDto.setUpdated_date(product.getUpdated_date());

        List<ProductDetailDto> productDetailDtoList = new ArrayList<>();
        Double priceMin = Double.valueOf(100000000);
        for (ProductDetail productDetail :
                product.getProductDetail()) {
            if (productDetail.getPrice() < priceMin) {
                priceMin = productDetail.getPrice();
            }
            ProductDetailDto productDetailDto = new ProductDetailDto();
            productDetailDto.setId(productDetail.getId());
            productDetailDto.setProductId(product.getId());
            productDetailDto.setColor(productDetail.getColor());
            productDetailDto.setSize(productDetail.getSize());
            productDetailDto.setPrice(productDetail.getPrice());
            productDetailDto.setQuantity(productDetail.getQuantity());
            productDetailDto.setBarcode(productDetail.getBarcode());
        }
        productDto.setPrice(priceMin);
        productDto.setProductDetailDtos(productDetailDtoList);
        return productDto;
    }
}
