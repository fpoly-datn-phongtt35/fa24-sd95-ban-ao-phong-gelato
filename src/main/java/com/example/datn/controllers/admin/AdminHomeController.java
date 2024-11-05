package com.example.datn.controllers.admin;

import com.example.datn.services.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class AdminHomeController {


    private final AccountService accountService;

    public AdminHomeController( AccountService accountService) {

        this.accountService = accountService;
    }

    @GetMapping("/admin")
    public String viewAdminHome(Model model) {
        return "/admin/index";
    }
}
