package com.example.sd18310.controller;

import com.example.sd18310.entity.CTSP;
import com.example.sd18310.entity.MauSac;
import com.example.sd18310.entity.SanPham;
import com.example.sd18310.entity.Size;
import com.example.sd18310.repository.CTSPRepository;
import com.example.sd18310.repository.MauSacRepository;
import com.example.sd18310.repository.SanPhamRepository;
import com.example.sd18310.repository.SizeRepository;
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
public class CTSPController {
    @Autowired
    CTSPRepository ctspRepository;

    @Autowired
    SanPhamRepository sanPhamRepository;

    @Autowired
    MauSacRepository mauSacRepository;

    @Autowired
    SizeRepository sizeRepository;

    @ModelAttribute("lopSP")
    public List<SanPham> getListSanPham(){
        return sanPhamRepository.findAll();
    }

    @ModelAttribute("lopMS")
    public List<MauSac> getListMauSac(){
        return mauSacRepository.findAll();
    }

    @ModelAttribute("lopSize")
    public List<Size> getListSize(){
        return sizeRepository.findAll();
    }

    @GetMapping("/ctsp/hien-thi")
    public String hienThi(Model model){
        List<CTSP> list = ctspRepository.findAll();
        model.addAttribute("list",list);
        return "/ctsp/hien-thi";
    }

    @GetMapping("/ctsp/delete")
    public String deleta(@RequestParam("id") Integer id){
        ctspRepository.deleteById(id);
        return "redirect:/ctsp/hien-thi";
    }
    @PostMapping("/ctsp/add")
    public String add(CTSP ctsp){
        ctsp.setNgay_sua(new Date());
        ctsp.setNgay_tao(new Date());
        ctspRepository.save(ctsp);
        return "redirect:/ctsp/hien-thi";
    }

    @GetMapping("/ctsp/detail")
    public String detail(@RequestParam("id") Integer id,
                         Model model){
        CTSP ctsp = ctspRepository.getReferenceById(id);
        model.addAttribute("ctsp",ctsp);
        return "/ctsp/detail";
    }
    @PostMapping("/ctsp/update")
    public String update(CTSP ctsp){
        CTSP ctspDetail = ctspRepository.getReferenceById(ctsp.getId());
        if(ctspDetail!=null){
            ctsp.setNgay_sua(new Date());
            ctsp.setNgay_tao(ctspDetail.getNgay_tao());
            ctspRepository.save(ctsp);
        }
        return "redirect:/ctsp/hien-thi";
    }
}
