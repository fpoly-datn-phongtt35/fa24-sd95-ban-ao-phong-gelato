package com.example.datn.controllers;


import com.example.datn.dto.product.ProductCreateDto;
import com.example.datn.dto.product.ProductDetailForm;
import com.example.datn.entities.*;
import com.example.datn.repositories.*;
import com.example.datn.services.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping
    public String getProductToTheme(@RequestParam (name = "page", defaultValue = "1") Integer page, Model model){
        Page<Product> listProduct = productService.getProduct(page);

        model.addAttribute("product", listProduct.getContent());
        model.addAttribute("totalPage", listProduct.getTotalPages());
        model.addAttribute("currentPage", page);

        return "product/product";
    }

    @GetMapping("/product/create")
    public String showCreateFormProduct(Model model, HttpSession httpSession){
        model.addAttribute("product", new Product());
        model.addAttribute("action", "/admin/product/create");

        return "product/product-create";
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute Product product, RedirectAttributes attributes, HttpSession httpSession){
        if (productService.uniqueCode(product.getCode())){
            attributes.addFlashAttribute("duplicateCode", "Mã sản phẩm đã tồn tại !");
        }
        String randomCode = UUID.randomUUID().toString();

        httpSession.setAttribute("randomCode", randomCode);
        httpSession.setAttribute("product_create" + randomCode, product);

        return "redirect:/admin/product";
    }

    @GetMapping("/product-detail/create")
    public String showProductDetailForm(Model model, HttpSession session){
        String ramdonCreateKey = (String) session.getAttribute("ramdonCreateKey");

        Product getDataProductCreate = (Product) session.getAttribute("dataProductCreate" + ramdonCreateKey);
        if (getDataProductCreate == null){
            return "redirect://admin/product/create"; // Back form create Product
        }

        ProductDetailForm productDetailForm = new ProductDetailForm();
        List<ProductDetail> listProductDetail = new ArrayList<>();

        model.addAttribute("product_detail", new ProductDetail());
        model.addAttribute("action", "/admin/product-detail/add");

        return "product-detail/product-detail-create";
    }

    @PostMapping("/product/create")
    public String addProductPartOne(@ModelAttribute ProductCreateDto createDto, RedirectAttributes attributes){
        productService.createProduct(createDto);
        attributes.addFlashAttribute("message", "Add product successfully !");

        return "redirect:/admin";
    }
}
