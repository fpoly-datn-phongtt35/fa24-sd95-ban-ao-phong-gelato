package com.example.datn.dto.DiscountProduct;

import lombok.Data;

import java.util.Date;

@Data
public class DiscountProductDto {
    private Long id;
    private Double discountedAmount;
    private Date startDate;
    private Date endDate;
    private boolean closed;
    private Long productDetailId;

//    private Long id;
//    private Double discountedAmount;
//    private LocalDateTime startDate;
//    private LocalDateTime endDate;
//    private boolean closed;
//    private Long productDetailId;
//
//    // Constructor không tham số
//    public DiscountProductDto() {
//    }
//    /// Constructor nhận DiscountProduct làm tham số
//    public DiscountProductDto(DiscountProduct discountProduct) {
//        this.id = discountProduct.getId();
//        this.discountedAmount = discountProduct.getDiscountedAmount();
//        this.startDate = discountProduct.getStartDate(); // Sử dụng LocalDateTime
//        this.endDate = discountProduct.getEndDate(); // Sử dụng LocalDateTime
//        this.closed = discountProduct.isClosed();
//        this.productDetailId = discountProduct.getProductDetail().getId();  // Giả sử có mối quan hệ giữa DiscountProduct và ProductDetail
//    }
}
