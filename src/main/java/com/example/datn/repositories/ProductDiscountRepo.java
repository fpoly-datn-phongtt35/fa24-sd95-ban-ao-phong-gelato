package com.example.datn.repositories;

import com.example.datn.entities.DiscountProduct;
import com.example.datn.entities.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface ProductDiscountRepo extends JpaRepository<DiscountProduct, Long> {
    // Tìm tất cả giảm giá có ProductDetail không rỗng với phân trang
    Page<DiscountProduct> findAllByProductDetailNotNull(Pageable pageable); //Trả về tất cả các bản ghi trong DiscountProduct mà trường productDetail không phải là null, kèm theo phân trang
    List<DiscountProduct> findAllByProductDetailIn(List<ProductDetail> productDetails);

//        @Query( value = "SELECT * FROM product_discount WHERE closed = 0", nativeQuery = true)
//    List<DiscountProduct> findAllByClosedFalse(); // Phương thức để lấy danh sách các đợt giảm giá chưa đóng

//    @Query(value = "SELECT * FROM product_discount WHERE closed = false", nativeQuery = true)
//    List<DiscountProduct> findAllByClosedFalse();

    // Tìm giảm giá theo product detail ID
    DiscountProduct findAllByProductDetail_Id(Long productDetailId);
    // Truy vấn tuỳ chỉnh: Tìm các giảm giá còn hiệu lực theo product detail ID

    @Query(value = "SELECT * FROM product_discount pd " +
            "WHERE pd.product_detail_id = :productDetailId " +
            "AND GETDATE() BETWEEN pd.start_date AND pd.end_date " +
            "AND pd.closed = 'false'", nativeQuery = true)
    DiscountProduct findValidDiscountByProductDetailId(@Param("productDetailId") Long productDetailId);
}
