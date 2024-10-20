package com.example.datn.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private LocalDateTime createDate;

    @Column(nullable = false)
    private boolean deleteFlag;

    @Nationalized //Đánh dấu trường này để lưu trữ chuỗi Unicode, cho phép lưu trữ ký tự không thuộc bảng mã ASCII.
    private String describe;

    @Column(nullable = false) //Trường này cũng không thể chứa giá trị null.
    private int gender;

    @Nationalized
    private String name;

    @Column(nullable = false)
    private double price;

    private int status;
    private LocalDateTime updatedDate;

    @ManyToOne  //Chỉ ra rằng một sản phẩm có thể thuộc về một danh mục (category) duy nhất
    @JoinColumn(name = "category_id") //Xác định tên cột trong bảng product sẽ liên kết với khóa chính trong bảng category
    //@JsonIgnore
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    //@JsonIgnore
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "material_id")
    //@JsonIgnore
    private Material material;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)  //mappedBy = "product": Cho biết rằng quan hệ này được quản lý bởi trường product trong lớp Image.
    //mappedBy = "product": Cho biết rằng quan hệ này được quản lý bởi trường product trong lớp Image.
    //cascade = CascadeType.ALL: Tất cả các hành động (persist, merge, remove, v.v.) sẽ được thực hiện cho các hình ảnh liên quan khi thực hiện trên sản phẩm.
    //orphanRemoval = true: Nếu một hình ảnh không còn được liên kết với sản phẩm nào, nó sẽ tự động bị xóa khỏi cơ sở dữ liệu.
    //@JsonIgnore
    private List<Image> image;
    //@JsonManagedReference
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductDetail> productDetails;
}
