package com.example.datn.controllers.admin;

import com.example.datn.entities.Image;
import com.example.datn.exceptions.NotFoundException;
import com.example.datn.services.ImageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
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
