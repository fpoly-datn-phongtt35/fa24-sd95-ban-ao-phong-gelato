package com.example.datn.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime create_date;

    private String file_type;

    private String link;

    private String name;

    private LocalDateTime update_date;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
