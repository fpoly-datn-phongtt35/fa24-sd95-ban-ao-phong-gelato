package com.example.sd18310.controller;

import com.example.sd18310.entity.HoaDon;
import com.example.sd18310.entity.KhachHang;
import com.example.sd18310.repository.HoaDonRepository;
import com.example.sd18310.repository.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
public class HoaDonController {
    @Autowired
    HoaDonRepository hoaDonRepository;

    @Autowired
    KhachHangRepository khachHangRepository;

    @ModelAttribute("lopKH")
    public List<KhachHang> getListKhachHang(){
        return khachHangRepository.findAll();
    }

    @GetMapping("/hoa-don/hien-thi")
    public String hienThi(Model model){
        List<HoaDon> list = hoaDonRepository.findAll();
        model.addAttribute("list",list);
        return "/hoa-don/hien-thi";
    }

    @GetMapping("/hoa-don/delete")
    public String delete(@RequestParam("id") Integer id){
        hoaDonRepository.deleteById(id);
        return "redirect:/hoa-don/hien-thi";
    }

    @PostMapping("/hoa-don/add")
    public String add(HoaDon hoaDon){
        hoaDon.setNgay_tao(new Date());
        hoaDon.setNgay_sua(new Date());
        hoaDonRepository.save(hoaDon);
        return "redirect:/hoa-don/hien-thi";
    }

    @GetMapping("/hoa-don/detail")
    public String detail(@RequestParam("id") Integer id,
                         Model model){
        HoaDon hoaDon = hoaDonRepository.getReferenceById(id);
        model.addAttribute("hd",hoaDon);
        return "/hoa-don/detail";
    }

    @PostMapping("/hoa-don/update")
    public String update(HoaDon hoaDon){
        HoaDon hoaDonDetail = hoaDonRepository.getReferenceById(hoaDon.getId());
        if(hoaDonDetail!=null){
            hoaDon.setNgay_sua(new Date());
            hoaDon.setNgay_tao(hoaDonDetail.getNgay_tao());
            hoaDonRepository.save(hoaDon);
        }
        return "redirect:/hoa-don/hien-thi";
    }
}
