package com.example.datn.utils;

import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;

public class ImageUploadUtils {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ImageUploadUtils.class);

    // Config uploads image and save image
    public static String saveImageFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadImagePath = Paths.get(uploadDir);
        if (!Files.exists(uploadImagePath)){
            Files.createDirectories(uploadImagePath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            String fileNameEnd = System.currentTimeMillis() + "_" + fileName;
            Path filePath = uploadImagePath.resolve(fileNameEnd);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return fileNameEnd;
        } catch (IOException e){
            throw new IOException("Could not delete file: " + fileName, e);
        }
    }

    public static void deleteFile(String filePath) throws IOException {
        try {
            Path path = Paths.get(filePath);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new IOException("Could not delete file: " + filePath, e);
        }
    }

    public static void cleanDir(String dir) {
        Path dirPath = Paths.get(dir);
        try {
            Files.list(dirPath).forEach(file -> {
                if (!Files.isDirectory(file)) {
                    try {
                        Files.delete(file);
                    } catch (IOException e) {
                        System.out.println("Could not delete file: " + file);
                    }
                }
            });
        } catch (IOException e) {
            System.out.println("Could not list directory " + dirPath);
        }
    }
}
