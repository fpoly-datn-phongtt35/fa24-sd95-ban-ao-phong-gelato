package com.example.datn.controllers.user;



import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {


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
    public String getHome(Model model) {
         return "user/home";

    }

}
