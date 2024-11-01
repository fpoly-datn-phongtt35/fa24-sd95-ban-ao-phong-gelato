package com.example.datn.controllers.admin;

import com.example.datn.entities.Brand;
import com.example.datn.entities.Color;
import com.example.datn.entities.Image;
import com.example.datn.entities.Material;
import com.example.datn.entities.Product;
import com.example.datn.entities.ProductDetail;
import com.example.datn.entities.Size;
import com.example.datn.repositories.ProductRepository;
import com.example.datn.services.*;
import com.example.datn.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class ProductDetailController {

    private static final String UPLOAD_DIRECTORY = "uploads";


    private Product productInLine;
    private final List<Image> imageList = new ArrayList<>();
    private long idImage;

    @Value("${upload.directory}")
    private String uploadDirectory;

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    // Phương thức GET để lấy trang chi tiết sản phẩm
    @GetMapping("/chi-tiet-san-pham/{code}")
    public String getProductDetailPage(@PathVariable String code, Model model) {
        Product product = productService.getProductByCode(code);

        if (product != null) {
            model.addAttribute("product", product);
            model.addAttribute("productDetails", product.getProductDetails());
            return "admin/product-detail";
        } else {
            System.err.println("Product not found for code: " + code);
            model.addAttribute("error", "Product not found");
            return "error/404";
        }
    }

    // Phương thức GET để lấy trang chỉnh sửa sản phẩm
    @GetMapping("/edit-san-pham/{code}")
    public String getProductEditPage(@PathVariable String code, Model model) {
        Product product = productService.getProductByCode(code);
        if (product == null) {
            model.addAttribute("error", "Product not found");
            return "error/404";
        }

        model.addAttribute("product", product);
        model.addAttribute("productDetails", product.getProductDetails());
        model.addAttribute("listBrand", brandService.getAll());
        model.addAttribute("listMaterial", materialService.getAll());
        model.addAttribute("listColor", colorService.findAll());
        model.addAttribute("listSize", sizeService.getAll());

        return "admin/product-edit-view"; // Tên của template chỉnh sửa sản phẩm
    }

    @PostMapping("/save-product-edit")
    @Transactional
    public String saveProductEdit(@ModelAttribute("product") Product product,
                                  @RequestParam("newImages") List<MultipartFile> newImages,
                                  @RequestParam(value = "imageRemoveIds", required = false) List<Long> imageRemoveIds,
                                  RedirectAttributes redirectAttributes) throws IOException {
        // Kiểm tra xem sản phẩm có tồn tại không dựa vào mã code hoặc id
        Product existingProduct = productService.getProductByCode(product.getCode());

        if (existingProduct != null) {
            // Cập nhật các thuộc tính của sản phẩm hiện có
            existingProduct.setName(product.getName());
            existingProduct.setDescribe(product.getDescribe());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setBrand(product.getBrand());
            existingProduct.setMaterial(product.getMaterial());
            existingProduct.setStatus(product.getStatus());
            existingProduct.setUpdatedDate(LocalDateTime.now()); // Cập nhật ngày chỉnh sửa cuối

            // Cập nhật danh sách productDetails
            for (int i = 0; i < product.getProductDetails().size(); i++) {
                ProductDetail detail = product.getProductDetails().get(i);
                ProductDetail existingDetail = existingProduct.getProductDetails().get(i);

                existingDetail.setQuantity(detail.getQuantity());
                existingDetail.setPrice(detail.getPrice());
                existingDetail.setColor(detail.getColor());
                existingDetail.setSize(detail.getSize());
            }

            // Xử lý ảnh hiện có (xóa ảnh nếu cần)
            if (imageRemoveIds != null) {
                List<Image> imagesToKeep = existingProduct.getImage().stream()
                        .filter(image -> !imageRemoveIds.contains(image.getId()))
                        .collect(Collectors.toList());

                // Xóa các ảnh từ database và hệ thống tập tin
                for (Long imageId : imageRemoveIds) {
                    Image imageToRemove = imageService.getImageById(imageId);
                    if (imageToRemove != null) {
                        FileUploadUtil.deleteFile(imageToRemove.getPath()); // Xóa file khỏi hệ thống
                    }
                }

                existingProduct.getImage().clear(); // Xóa tất cả các ảnh khỏi sản phẩm
                existingProduct.getImage().addAll(imagesToKeep); // Chỉ giữ lại ảnh không bị xóa
                imageService.removeImageByIds(imageRemoveIds); // Xóa ảnh khỏi database
            }

            // Thêm ảnh mới vào danh sách nếu có
            if (!newImages.isEmpty()) {
                for (MultipartFile file : newImages) {
                    String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
                    String fileNameAfter = FileUploadUtil.saveFile(uploadDirectory, fileName, file);
                    Image newImage = new Image(null, fileNameAfter, LocalDateTime.now(), LocalDateTime.now(),
                            "uploads/" + fileNameAfter, file.getContentType(), existingProduct);
                    existingProduct.getImage().add(newImage);
                }
            }

            // Lưu lại đối tượng đã cập nhật
            productRepository.save(existingProduct);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật sản phẩm thành công!");
        } else {
            // Nếu sản phẩm không tồn tại, thêm mới
            productRepository.save(product);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm sản phẩm mới thành công!");
        }
        return "redirect:/admin/chi-tiet-san-pham/" + existingProduct.getCode();
    }





    // Đưa dữ liệu danh sách kích cỡ và màu sắc vào Model
    @ModelAttribute("listSize")
    public List<Size> getSize() {
        return sizeService.getAll();
    }

    @ModelAttribute("listColor")
    public List<Color> getColor() {
        return colorService.findAll();
    }

    @ModelAttribute("listBrand")
    public List<Brand> getBrand() {
        return brandService.getAll();
    }

    @ModelAttribute("listMaterial")
    public List<Material> getMaterial() {
        return materialService.getAll();
    }
}
