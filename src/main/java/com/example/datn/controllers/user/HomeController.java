package com.example.datn.controllers.user;



import com.example.datn.dto.Product.ProductDto;
import com.example.datn.dto.Product.SearchProductDto;
import com.example.datn.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class HomeController {
    @Autowired
    private ProductService productService;

    @GetMapping("/home")
    public String gethome(Model model) {
        var authors= SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        if(
                authors.stream().map(author->author.getAuthority()).anyMatch(s ->
                        s.equalsIgnoreCase("ROLE_USER")||
                        s.equalsIgnoreCase("ROLE_CUSTOMER")||
                        s.equalsIgnoreCase("ROLE_ANONYMOUS"))
               )
        {
            return "user/home";
        }
        else{
            return "redirect:/admin";
        }

    }
    @GetMapping("/")
    public String gethome(Model model, SearchProductDto searchProductDto, @PageableDefault(size = 20, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ProductDto> products = productService.searchProduct(searchProductDto, pageable);


        if (searchProductDto != null) {
            int pageNumber = pageable.getPageNumber();
            int pageSize = pageable.getPageSize();
            Sort sort = pageable.getSort();
//            String url = "&size=" + pageSize;
            String url = "";


            if (searchProductDto.getKeyword() != null) {
                url += "&keyword=" + searchProductDto.getKeyword();
            }


            if (sort.isSorted()) {
                List<Sort.Order> orders = sort.toList();

                // Tạo một danh sách để lưu trữ chuỗi sắp xếp cho mỗi trường
                List<String> sortStrings = new ArrayList<>();

                for (Sort.Order order : orders) {
                    // Lấy tên trường (field)
                    String property = order.getProperty();

                    // Kiểm tra xem có phải là sắp xếp giảm dần không
                    boolean isDescending = order.isDescending();

                    // Tạo chuỗi sắp xếp dạng "field,asc" hoặc "field,desc"
                    String sortString = property + "," + (isDescending ? "desc" : "asc");

                    // Thêm chuỗi sắp xếp vào danh sách
                    sortStrings.add(sortString);
                }
                url += "&sort=" + String.join(",", sortStrings);
                searchProductDto.setSort(String.join(",", sortStrings));
            }

            if (searchProductDto.getMinPrice() != null) {
                url += "&minPrice=" + searchProductDto.getMinPrice();
            }
            if (searchProductDto.getMinPrice() != null) {
                url += "&maxPrice=" + searchProductDto.getMaxPrice();
            }
            if (searchProductDto.getCategoryId() != null) {
                url += "&category=" + searchProductDto.getCategoryId().stream()
                        .map(Object::toString) // Chuyển đổi mỗi số thành chuỗi
                        .collect(Collectors.joining(","));
            }
            if (searchProductDto.getGender() != null) {
                url += "&gender=" + searchProductDto.getGender();
            }
            model.addAttribute("url", url);
        }

        model.addAttribute("products", products);
        model.addAttribute("dataFilter", searchProductDto);

        return "user/home";
    }

}
