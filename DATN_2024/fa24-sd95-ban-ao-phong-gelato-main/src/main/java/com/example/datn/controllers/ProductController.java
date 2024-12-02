package com.example.datn.controllers;


import com.example.datn.dto.product.ProductCreateDto;
import com.example.datn.dto.product.ProductDetailForm;
import com.example.datn.dto.product.ProductDto;
import com.example.datn.entities.Brand;
import com.example.datn.entities.Color;
import com.example.datn.entities.Material;
import com.example.datn.entities.Product;
import com.example.datn.entities.ProductDetail;
import com.example.datn.entities.Size;
import com.example.datn.repositories.BrandRepository;
import com.example.datn.repositories.ColorRepository;
import com.example.datn.repositories.MaterialRepository;
import com.example.datn.repositories.ProductRepository;
import com.example.datn.repositories.SizeRepository;
import com.example.datn.services.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private SizeRepository sizeRepository;


    @Value("${upload.directory}")
    private String uploadDirectory;

    @ModelAttribute("brand")
    public List<Brand> getBrandToSelect(){
        return brandRepository.findAll();
    }

    @ModelAttribute("material")
    public List<Material> getMaterialToSelect(){
        return materialRepository.findAll();
    }

    @ModelAttribute("size")
    public List<Size> getSizeToSelect(){
        return sizeRepository.findAll();
    }

    @ModelAttribute("color")
    public List<Color> getColorToSelect(){
        return colorRepository.findAll();
    }

    @GetMapping("product-all")
    public String getProductToTheme(@RequestParam (name = "page", defaultValue = "1") Integer page, Model model){
        Page<Product> listProduct = productService.getProduct(page);

        model.addAttribute("product", listProduct.getContent());
        model.addAttribute("totalPage", listProduct.getTotalPages());
        model.addAttribute("currentPage", page);

        return "admin/product/product";
    }

    @GetMapping("/product-create")
    public String showCreateFormProduct(Model model, HttpSession httpSession){
        model.addAttribute("product", new Product());
        model.addAttribute("brands", brandRepository.findAll());
        model.addAttribute("materials", materialRepository.findAll());
        model.addAttribute("action", "/admin/product-create");

        return "admin/product/product-create";
    }

    @PostMapping("/product-create")
    public String createProduct(@ModelAttribute ProductDto productDto,
                                @RequestParam("image") MultipartFile[] files,
                                RedirectAttributes redirectAttributes) {
        try {
            // Gọi service để lưu sản phẩm và hình ảnh
            productService.saveProductWithImages(productDto, files);
            redirectAttributes.addFlashAttribute("message", "Tạo sản phẩm thành công.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Tạo sản phẩm thất bại.");
            e.printStackTrace();
        }
        return "redirect:/admin/product-all";
    }


    @GetMapping("/product-detail/create")
    public String showProductDetailForm(Model model, HttpSession session){
        String ramdonCreateKey = (String) session.getAttribute("ramdonCreateKey");


        Product getDataProductCreate = (Product) session.getAttribute("dataProductCreate" + ramdonCreateKey);
        if (getDataProductCreate == null){
            return "redirect:/admin/product-create"; // Back form create Product
        }

        ProductDetailForm productDetailForm = new ProductDetailForm();
        List<ProductDetail> listProductDetail = new ArrayList<>();

        model.addAttribute("product_detail", new ProductDetail());
        model.addAttribute("action", "/admin/product-detail/add");

        return "admin/product-detail/product-detail-create";
    }

    @PostMapping("/product/create")
    public String addProductPartOne(@ModelAttribute ProductCreateDto createDto, RedirectAttributes attributes){
        productService.createProduct(createDto);
        attributes.addFlashAttribute("message", "Add product successfully !");

        return "redirect:/admin/product-all";
    }
}
