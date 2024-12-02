package com.example.datn.controllers.admin;


import com.example.datn.entities.Account;
import com.example.datn.security.CustomUserDetails;
import com.example.datn.services.AccountService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AccountMngController {
    private final AccountService accountService;

    public AccountMngController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/admin-only/account-management")
    public String viewAccountManagementPage(Model model) {
        List<Account> accountList = accountService.findAllAccount();
        model.addAttribute("accountList", accountList);
        return "/admin/account";
    }

    @PostMapping("/account/block/{id}")
    public String blockAccount(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        var accountPresent = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (accountPresent.getAccount().getId().equals(id)) {
            redirectAttributes.addFlashAttribute("errMessage", "Bạn không thể tự khóa tài khoản của mình");
            return "redirect:/admin-only/account-management";
        }
        if (accountService.countAllByRole_IdAndIsNonLockedTrue(1) <= 2) {
            redirectAttributes.addFlashAttribute("errMessage", "Cần tối thiểu 2 tài khoản quản lý");
            return "redirect:/admin-only/account-management";
        }
        Account account = accountService.blockAccount(id);

        redirectAttributes.addFlashAttribute("message", "Tài khoản " + account.getEmail() + " đã khóa thành công");
        return "redirect:/admin-only/account-management";


    }

    @PostMapping("/account/open/{id}")
    public String openAccount(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Account account = accountService.openAccount(id);
        redirectAttributes.addFlashAttribute("message", "Tài khoản " + account.getEmail() + " đã mở khóa thành công");
        return "redirect:/admin-only/account-management";
    }

    @PostMapping("/account/change-role")
    public String openAccount(@ModelAttribute("email") String email, @ModelAttribute("role") Long roleId, RedirectAttributes redirectAttributes) {
        if (accountService.countAllByRole_IdAndIsNonLockedTrue(1) <= 2) {
            redirectAttributes.addFlashAttribute("errMessage", "Cần tối thiểu 2 tài khoản quản lý");
            return "redirect:/admin-only/account-management";
        }
        Account account = accountService.changeRole(email, roleId);
        redirectAttributes.addFlashAttribute("message", "Tài khoản " + account.getEmail() + " đã đổi thành quyền thành công");
        return "redirect:/admin-only/account-management";
    }
}
