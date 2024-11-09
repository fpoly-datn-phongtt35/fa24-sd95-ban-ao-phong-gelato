package com.example.datn.repositories;


import com.example.datn.entities.Color;
import com.example.datn.entities.ProductDetail;
import com.example.datn.entities.ProductDiscount;
import com.example.datn.entities.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDiscountRepository extends JpaRepository<ProductDiscount, Long> {
    Page<ProductDiscount> findAllByProductDetailNotNull(Pageable pageable);
    List<ProductDiscount> findAllByProductDetailIn(List<ProductDetail> productDetails);
    ProductDiscount findByProductDetail_Id(Long productDetailId);

    @Query(value = "SELECT * FROM product_discount pd " +
            "WHERE pd.product_detail_id = :productDetailId " +
            "AND GETDATE() BETWEEN pd.start_date AND pd.end_date " +
            "AND pd.closed = 'false'", nativeQuery = true)
    ProductDiscount findValidDiscountByProductDetailId(@Param("productDetailId") Long productDetailId);

    // Thêm phương thức truy vấn dựa trên đối tượng Color
    List<ProductDiscount> findByProductDetail_Color(Color color);

    // Thêm phương thức truy vấn dựa trên đối tượng Size
    List<ProductDiscount> findByProductDetail_Size(Size size);

    // Thêm phương thức truy vấn dựa trên đối tượng Color và Size
    List<ProductDiscount> findByProductDetail_ColorAndProductDetail_Size(Color color, Size size);


    //findByColor : Truy vấn tất cả các giảm giá theo màu sắc cụ thể.

    //findBySize: Truy vấn tất cả các giảm giá theo kích cỡ cụ thể.

    //findByColorAndSize: Truy vấn tất cả các giảm giá theo cả màu sắc và kích cỡ.
}
