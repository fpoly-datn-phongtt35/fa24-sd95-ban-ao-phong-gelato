package com.example.datn.controllers.admin;

import com.example.datn.dto.Bill.BillDtoInterface;
import com.example.datn.dto.Product.ProductDto;
import com.example.datn.entities.Account;
import com.example.datn.repositories.BillRepository;
import com.example.datn.services.AccountService;
import com.example.datn.services.BillService;
import com.example.datn.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminHomeController {



        private final BillService billService;
        private final ProductService productService;
        private final BillRepository billRepository;
         private final AccountService accountService;

        public AdminHomeController(BillService billService, ProductService productService, BillRepository billRepository, AccountService accountService) {
            this.billService = billService;
            this.productService = productService;
            this.billRepository = billRepository;
            this.accountService = accountService;
        }

        @GetMapping("/admin")
        public String viewAdminHome(Model model) {
            Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "createDate"));
            Page<BillDtoInterface> billDtos = billService.findAll(pageable);

            Page<ProductDto> productDtos = productService.getAllProductApi(Pageable.ofSize(10));
            double totalRevenue = billRepository.calculateTotalRevenue();
            long totalBillWaiting = billRepository.getTotalBillStatusWaiting();
            long totalBillWaiting2 = billRepository.getTotalBillStatusWaiting2();

            // Truyền dữ liệu sang giao diện
            model.addAttribute("billList", billDtos.getContent());
            model.addAttribute("totalBillQuantity", totalBillWaiting2);
            model.addAttribute("totalProduct", productDtos.getTotalElements());
            model.addAttribute("revenue", totalRevenue);
            model.addAttribute("totalBillWaiting", totalBillWaiting);

            return "/admin/index";
        }

    }




