package com.example.sd18310.controller;

import com.example.sd18310.entity.DanhMuc;
import com.example.sd18310.repository.DanhMucRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
public class DanhMucController {

    @Autowired
    DanhMucRepository danhMucRepository;

    @GetMapping("/danh-muc/hien-thi")
    public String hienThi(Model model){
        List<DanhMuc> list = danhMucRepository.findAll();
        model.addAttribute("list",list);
        return "/danh-muc/hien-thi";
    }

    @GetMapping("/danh-muc/delete")
    public String delete(@RequestParam("id")Integer id){
        danhMucRepository.deleteById(id);
        return "redirect:/danh-muc/hien-thi";
    }

    @GetMapping("/danh-muc/detail")
    public String detail(@RequestParam("id")Integer id,
                         Model model){
        DanhMuc danhMuc = danhMucRepository.getReferenceById(id);
        model.addAttribute("danhMuc",danhMuc);
        return "/danh-muc/detail";
    }

    @PostMapping("/danh-muc/add")
    public String add(DanhMuc danhMuc){
        danhMucRepository.save(danhMuc);
        return "redirect:/danh-muc/hien-thi";
    }

    @PostMapping("/danh-muc/update")
    public String update(DanhMuc danhMuc){
        DanhMuc danhMucDetail = danhMucRepository.getReferenceById(danhMuc.getId());
        if(danhMucDetail!=null){
            danhMuc.setNgay_sua(new Date());
            danhMuc.setNgay_tao(danhMucDetail.getNgay_tao());
            danhMucRepository.save(danhMuc);
        }
        return "redirect:/danh-muc/hien-thi";
    }
}
