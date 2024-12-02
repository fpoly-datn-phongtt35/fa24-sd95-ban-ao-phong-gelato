package com.example.datn.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Color")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Color implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;

    @Nationalized
    private String name;

    private Boolean deleteFlag;
}