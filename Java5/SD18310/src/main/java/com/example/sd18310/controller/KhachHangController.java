package com.example.sd18310.controller;

import com.example.sd18310.entity.KhachHang;
import com.example.sd18310.repository.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
public class KhachHangController {

    @Autowired
    KhachHangRepository khachHangRepository;

    @GetMapping("/khach-hang/hien-thi")
    public String hienThi(Model model){
        List<KhachHang> list = khachHangRepository.findAll();
        model.addAttribute("list",list);
        return "/khach-hang/hien-thi";
    }

    @PostMapping("/khach-hang/add")
    public String add(KhachHang khachHang){
        khachHang.setNgay_sua(new Date());
        khachHang.setNgay_tao(new Date());
        khachHangRepository.save(khachHang);
        return "redirect:/khach-hang/hien-thi";
    }
    @GetMapping("/khach-hang/delete")
    public String delete(@RequestParam("id") Integer id){
        khachHangRepository.deleteById(id);
        return "redirect:/khach-hang/hien-thi";
    }
    @GetMapping("/khach-hang/detail")
    public String detail(@RequestParam("id")Integer id,
                         Model model){
        KhachHang khachHang = khachHangRepository.getReferenceById(id);
        model.addAttribute("khachHang",khachHang);
        return "/khach-hang/detail";
    }
    @PostMapping("/khach-hang/update")
    public String update(KhachHang khachHang){
        KhachHang khachHangDetail = khachHangRepository.getReferenceById(khachHang.getId());
        if(khachHangDetail!=null){
            khachHang.setNgay_sua(new Date());
            khachHang.setNgay_tao(khachHangDetail.getNgay_tao());
            khachHangRepository.save(khachHang);
        }
        return "redirect:/khach-hang/hien-thi";
    }
}
