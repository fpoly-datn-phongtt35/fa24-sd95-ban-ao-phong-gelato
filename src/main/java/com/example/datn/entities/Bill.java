package com.example.datn.entities;

//import com.project.DuAnTotNghiep.entity.enumClass.BillStatus;
//import com.project.DuAnTotNghiep.entity.enumClass.InvoiceType;
import com.example.datn.entities.enumclass.BillStatus;
import com.example.datn.entities.enumclass.InvoiceType;
import lombok.*;
import org.hibernate.annotations.Nationalized;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "bill")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bill implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String code;
    private double promotionPrice;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    private String status;
//    @Enumerated(EnumType.STRING)
//    @Column(name = "status")
//    private BillStatus status;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "invoice_type")
//    private InvoiceType invoiceType;
private String invoiceType;
    @Nationalized
    private String billingAddress;

    private Double amount;

    private Boolean returnStatus;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "customerId")
//    private Customer customer;
  private String customer;
//    @ManyToOne
//    @JoinColumn(name = "paymentMethodId")
//    private PaymentMethod paymentMethod;
 private String paymentMethodId;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bill")
//    private List<BillDetail> billDetail;
    private String billDetail;
//    @ManyToOne
//    @JoinColumn(name = "discount_code_id")
//    private DiscountCode discountCode;
    private String discountCode;
}