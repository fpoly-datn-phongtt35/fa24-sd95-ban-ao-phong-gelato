package com.example.datn.entities;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderId;
    private String amount;
    private String orderStatus;
    private LocalDateTime paymentDate;
    private Integer statusExchange;

    @OneToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;
}
