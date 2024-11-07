package com.example.sd18310.controller;

import com.example.sd18310.entity.CTSP;
import com.example.sd18310.entity.HDCT;
import com.example.sd18310.entity.HoaDon;
import com.example.sd18310.repository.CTSPRepository;
import com.example.sd18310.repository.HDCTRepository;
import com.example.sd18310.repository.HoaDonRepository;
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
public class HDCTController {
    @Autowired
    HDCTRepository hdctRepository;

    @Autowired
    CTSPRepository ctspRepository;

    @Autowired
    HoaDonRepository hoaDonRepository;

    @ModelAttribute("lopCTSP")
    public List<CTSP> getListCTSP(){
        return ctspRepository.findAll();
    }

    @ModelAttribute("lopHD")
    public List<HoaDon> getListHoaDon(){
        return hoaDonRepository.findAll();
    }

    @GetMapping("/hdct/hien-thi")
    public String hienThi(Model model){
        List<HDCT> list = hdctRepository.findAll();
        model.addAttribute("list",list);
        return "/hdct/hien-thi";
    }

    @GetMapping("/hdct/delete")
    public String delete(@RequestParam("id") Integer id){
        hdctRepository.deleteById(id);
        return "redirect:/hdct/hien-thi";
    }

    @PostMapping("/hdct/add")
    public String add(HDCT hdct){
        hdct.setNgay_tao(new Date());
        hdct.setNgay_sua(new Date());
        hdctRepository.save(hdct);
        return "redirect:/hdct/hien-thi";
    }

    @GetMapping("/hdct/detail")
    public String detail(@RequestParam("id")Integer id,
                         Model model){
        HDCT hdct = hdctRepository.getReferenceById(id);
        model.addAttribute("hdct",hdct);
        return "/hdct/detail";
    }

    @PostMapping("/hdct/update")
    public String update(HDCT hdct){
        HDCT hdctDetail = hdctRepository.getReferenceById(hdct.getId());
        if(hdctDetail!=null){
            hdct.setNgay_sua(new Date());
            hdct.setNgay_tao(hdctDetail.getNgay_tao());
            hdctRepository.save(hdct);
        }
        return "redirect:/hdct/hien-thi";
    }
}
