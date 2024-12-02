package com.example.sd18310.controller;

import com.example.sd18310.entity.DanhMuc;
import com.example.sd18310.entity.SanPham;
import com.example.sd18310.repository.DanhMucRepository;
import com.example.sd18310.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
public class SanPhamController {

    @Autowired
    SanPhamRepository sanPhamRepository;
    @Autowired
    DanhMucRepository danhMucRepository;

    @GetMapping("/san-pham/hien-thi")
    public String hienThi(Model model) {
        List<SanPham> list = sanPhamRepository.findAll();
        model.addAttribute("list", list);
        return "san-pham";
    }

//    @ResponseBody

//    @GetMapping("/san-pham/hien-thi")
//    public String hienThi(
//            @RequestParam("page") Integer page,
//            Model model
//    ) {
//        Pageable pageable = PageRequest.of(page, 5);
//        Page<SanPham> list = sanPhamRepository.findAll(pageable);
//        model.addAttribute("page", list);
//        return "san-pham";
//    }

    @GetMapping("/san-pham/delete")
    public String delete(@RequestParam("id") Integer id){
        sanPhamRepository.deleteById(id);
        return "redirect:/san-pham/hien-thi";
    }

    @ModelAttribute("lopDM")
    public List<DanhMuc> getListDanhMuc(){
        return danhMucRepository.findAll();
    }

    @PostMapping("/san-pham/add")
    public String add(SanPham sanPham){
        sanPham.setNgaySua(new Date());
        sanPham.setNgay_tao(new Date());
        sanPhamRepository.save(sanPham);
        return "redirect:/san-pham/hien-thi";
    }

    @GetMapping("/san-pham/detail")
    public String detail(@RequestParam("id") Integer id,
                         Model model){
//        SanPham sanPham = sanPhamRepository.findById(id).get();
        SanPham sanPham = sanPhamRepository.getReferenceById(id);
        model.addAttribute("sanPham", sanPham);
        return "detail-san-pham";
    }

    @PostMapping("/san-pham/update")
    public String update(SanPham sanPham){
        SanPham sanPhamDetail = sanPhamRepository.getReferenceById(sanPham.getId());
        if (sanPhamDetail!=null){
            sanPham.setNgaySua(new Date());
            sanPham.setNgay_tao(sanPhamDetail.getNgay_tao());
            sanPhamRepository.save(sanPham);
        }
        return "redirect:/san-pham/hien-thi";
    }


}
