package com.example.demo.controller.admin;

import com.example.demo.entity.Image;
import com.example.demo.exception.NotFoundException;
import com.example.demo.service.ImageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/admin/product/{id}/images")
    List<Image> getAllImagesByProductId(@PathVariable Long id) throws NotFoundException {
        return imageService.getAllImagesByProductId(id);
    }
}
