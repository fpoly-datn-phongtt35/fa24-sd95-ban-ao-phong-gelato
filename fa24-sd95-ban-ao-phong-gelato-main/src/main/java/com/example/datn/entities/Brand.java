package com.example.datn.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Brand")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Brand implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    @Nationalized
    private String name;
    private int status;
    private Boolean deleteFlag;
}