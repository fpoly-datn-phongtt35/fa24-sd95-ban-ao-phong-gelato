package com.example.datn.controllers;

import com.example.datn.entities.Bill;
import com.example.datn.repositories.BillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/bill")
@Controller
public class BillController {

    @Autowired
    private BillRepo billRepo;
    @GetMapping("/list")
    public String listBills(Model model,
                            @RequestParam(value = "keyword", required = false) String keyword,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Bill> billPage;

        if (keyword != null && !keyword.isEmpty()) {
            // Tìm kiếm với từ khóa và phân trang
            billPage = billRepo.findByCodeContainingIgnoreCase(keyword, pageable);
        } else {
            // Phân trang thông thường
            billPage = billRepo.findAll(pageable);
        }

        model.addAttribute("billPage", billPage);
        model.addAttribute("bills", billPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("keyword", keyword);  // Giữ lại từ khóa tìm kiếm nếu có
        return "/bill";
    }
}
