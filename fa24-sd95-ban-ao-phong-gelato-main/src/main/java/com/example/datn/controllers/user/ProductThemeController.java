package com.example.datn.controllers.user;

import com.example.datn.dto.Product.ProductDto;
import com.example.datn.dto.Product.SearchProductDto;
import com.example.datn.entities.Brand;
import com.example.datn.entities.Product;
import com.example.datn.services.BrandService;
import com.example.datn.services.ColorService;
import com.example.datn.services.ProductDetailService;
import com.example.datn.services.ProductService;
import com.example.datn.services.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductThemeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private BrandService brandService;

    @GetMapping("/product")
    public String getProductTheme(Model model, SearchProductDto productDto, @PageableDefault(size = 18) Pageable pageable){
        List<Brand> listBrand = brandService.getAll();
        Page<ProductDto> products = productService.searchProduct(productDto, pageable);

        if (productDto != null){
            Integer pageNumber = pageable.getPageNumber();
            Integer pageSize = pageable.getPageSize();
            Sort sort = pageable.getSort();
            String url = "";

            if (productDto.getKeyword() != null){
                url += "&keyword=" + productDto.getKeyword();
            }

            if (sort.isSorted()){
                List<Sort.Order> orderList = sort.toList();
                List<String> listSortString = new ArrayList<>();
                for (Sort.Order order : orderList){
                    String property = order.getProperty();

                    boolean isDescending = order.isDescending();

                    String sortString = property + "," + (isDescending ? "desc" : "asc");

                    listSortString.add(sortString);
                }
                url += "&sort=" + String.join(",", listSortString);
                productDto.setSort(String.join(",", listSortString));
            }
            if (productDto.getMaxPrice() != null){
                url += "&minPrice=" + productDto.getMinPrice();
            }
            if (productDto.getMaxPrice() != null){
                url += "&maxPrice=" + productDto.getMinPrice();
            }
            if (productDto.getGender() != null){
                url += "&gender=" + productDto.getGender();
            }
            model.addAttribute("url", url);
        }

        model.addAttribute("dataFilter", productDto);
        model.addAttribute("brands", listBrand);
        model.addAttribute("products", products);

        return "user/shop-product";
    }

    @GetMapping("/product-detail/{productCode}")
    public String getProductDetail(Model model, @PathVariable String productCode) {
        Product product = productService.getProductByCode(productCode);
        if(product == null) {
            return "/error/404";
        }
        model.addAttribute("product", product);
        return "user/product-detail";
    }
}
