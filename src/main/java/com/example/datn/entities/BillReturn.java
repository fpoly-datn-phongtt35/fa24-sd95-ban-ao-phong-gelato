package com.example.datn.entities;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "bill_return")
public class BillReturn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @Nationalized
    private String returnReason;
    @Column(name = "return_date")
    private LocalDateTime returnDate;

    private Integer percentFeeExchange;
    @Column(name = "return_money")
    private Double returnMoney;

    private boolean isCancel;

    // 0:Chờ xác nhận 1: Chờ giao hàng 2: Đang giao hàng 3: Hoàn thành 4: Hủy
    @Column(name = "return_status")
    private int returnStatus;

    @OneToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;

    @OneToMany(mappedBy = "billReturn", cascade = CascadeType.ALL)
    private List<ReturnDetail> returnDetails;
    private Double returnVoucher;
}
