package com.example.datn.utils;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class QrcodeService {
    public static String generateQrcode(String text, String fileName) throws IOException {
        ByteArrayOutputStream stream = QRCode.from(text)
                .withSize(250,250)
                .to(ImageType.PNG)
                .stream();
        String uploadDir = "upload-barcode/";
        Path uploadPath = Paths.get(uploadDir);
        String filenameAfter = fileName + ".png";
        if (!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        Path filePath = uploadPath.resolve(filenameAfter);
        Files.write(filePath, stream.toByteArray());
        return "QR code saved at: " + uploadDir + fileName;
    }
}
