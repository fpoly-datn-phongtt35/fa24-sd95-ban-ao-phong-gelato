package com.example.sd18310.controller;

import com.example.sd18310.entity.MauSac;
import com.example.sd18310.repository.MauSacRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
public class MauSacController {
    @Autowired
    MauSacRepository mauSacRepository;

    @GetMapping("/mau-sac/hien-thi")
    public String hienThi(Model model){
        List<MauSac> ms = mauSacRepository.findAll();
        model.addAttribute("list",ms);
        return "/mau-sac/hien-thi";
    }

    @GetMapping("/mau-sac/delete")
    public String delete(@RequestParam("id") Integer id){
        mauSacRepository.deleteById(id);
        return "redirect:/mau-sac/hien-thi";
    }

    @PostMapping("/mau-sac/add")
    public String add(MauSac ms){
        ms.setNgay_tao(new Date());
        ms.setNgay_tao(new Date());
        mauSacRepository.save(ms);
        return "redirect:/mau-sac/hien-thi";
    }

    @GetMapping("/mau-sac/detail")
    public String detail(@RequestParam("id") Integer id,
                         Model model){
        MauSac ms = mauSacRepository.getReferenceById(id);
        model.addAttribute("mauSac",ms);
        return "/mau-sac/detail";
    }

    @PostMapping("/mau-sac/update")
    public String update(MauSac ms){
        MauSac msct = mauSacRepository.getReferenceById(ms.getId());
        if(msct!=null){
            ms.setNgay_sua(new Date());
            ms.setNgay_tao(msct.getNgay_tao());
            mauSacRepository.save(ms);
        }
        return "redirect:/mau-sac/hien-thi";
    }
}
