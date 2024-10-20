package com.example.datn.services;

import com.example.datn.dto.Product.ProductDto;
import com.example.datn.dto.Product.ProductSearchResultDto;
import com.example.datn.dto.Product.SearchProductDto;
import com.example.datn.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    Page<Product> getAllProduct(Pageable pageable);
    Page<ProductSearchResultDto>getAll(Pageable pageable);
    Product save(Product product) throws IOException;
    Product delete(Long id);
    Product getProductByCode(String code);
    boolean existsByCode(String code);

    Page<Product> search(String productName, Pageable pageable);

    Page<ProductSearchResultDto> listSearchProduct(String maSanPham,String tenSanPham,Long nhanHang,Long chatLieu,Long theLoai,Integer trangThai,Pageable pageable);

    Page<Product> getAllByStatus(int status, Pageable pageable);

    Optional<Product> getProductById(Long id);

    Page<ProductDto> searchProduct(SearchProductDto searchDto, Pageable pageable);

    Page<ProductDto> getAllProductApi(Pageable pageable);

    ProductDto getProductByBarcode(String barcode);

    List<ProductDto> getAllProductNoPaginationApi(SearchProductDto searchRequest);

    ProductDto getByProductDetailId(Long detailId);
}
