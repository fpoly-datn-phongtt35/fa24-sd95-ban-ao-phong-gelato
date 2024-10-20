package com.example.datn.repositories;

import com.example.datn.entities.Product;
import com.example.datn.entities.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailRepo extends JpaRepository<ProductDetail, Long> {
    // Phương thức để lấy danh sách ProductDetail với phân trang
    Page<ProductDetail> findByProductId(Long id, Pageable pageable);

    // Phương thức để lấy một ProductDetail dựa trên Product
//    ProductDetail findByProduct(Product product);
    ProductDetail getProductDetailByProduct(Product product);

    // Phương thức để lấy danh sách ProductDetail theo productId
    List<ProductDetail> getProductDetailByProductId(Long productId);

    // Phương thức để tìm kiếm theo barcode
    ProductDetail findByBarcodeContainingIgnoreCase(String barcode);

    // Phương thức kiểm tra sự tồn tại của barcode
    boolean existsByBarcode(String barcode);
}
