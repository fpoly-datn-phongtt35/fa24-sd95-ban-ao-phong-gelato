package com.example.datn.entities;
import com.example.datn.entities.enumClass.PaymentMethodName;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "payment_method")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethod implements Serializable{
    @Id
    private Long id;

    @Nationalized
    @Enumerated(EnumType.STRING)
    private PaymentMethodName name;
    private int status;
}
