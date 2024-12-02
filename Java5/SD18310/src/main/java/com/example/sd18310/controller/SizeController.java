package com.example.sd18310.controller;

import com.example.sd18310.entity.Size;
import com.example.sd18310.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class SizeController {
    @Autowired
    SizeRepository sizeRepository;

    @GetMapping("/size/hien-thi")
    public String hienThi(Model model){
//        List<Size> list = sizeRepository.findAll();
        model.addAttribute("size", sizeRepository.findAll());
        return "/size/hien-thi";
    }

    @GetMapping("/size/delete")
    public String delete(@RequestParam("id") Integer id){
        sizeRepository.deleteById(id);
        return "redirect:/size/hien-thi";
    }
//    @ModelAttribute("size")
    @PostMapping("/size/add")
    public String add(@Validated Size size, BindingResult error, Model model){
//        model.addAttribute("size",size);
        if(error.hasErrors()){

            System.out.println("lỗi");
            return "redirect:/size/hien-thi";
        }
        size.setNgay_tao(new Date());
        size.setNgay_sua(new Date());
        sizeRepository.save(size);
        return "redirect:/size/hien-thi";
    }

    @GetMapping("/size/detail")
    public String detail(@RequestParam("id") Integer id,
                         Model model){
        Size size = sizeRepository.getReferenceById(id);
        model.addAttribute("size",size);
        return "/size/detail";
    }

    @PostMapping("/size/update")
    public String update(Size size){
        Size sizeDetail = sizeRepository.getReferenceById(size.getId());
        if(sizeDetail!=null){
            size.setNgay_sua(new Date());
            size.setNgay_tao(sizeDetail.getNgay_tao());
            sizeRepository.save(size);
        }
        return "redirect:/size/hien-thi";
    }

}
