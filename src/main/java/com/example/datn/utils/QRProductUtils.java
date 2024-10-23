package com.example.datn.utils;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class QRProductUtils {

    public static String generateQRCode(String text, String fileNamePath) throws IOException {
        ByteArrayOutputStream stream = QRCode.from(text).withSize(250, 250)
                .to(ImageType.PNG).stream(); // Config size image qrCode

        String uploadFile = "upload_barcode";
        Path uploadsImagePath = Paths.get(uploadFile);

        String fileName = fileNamePath + ".png"; // end file
        if (!Files.exists(uploadsImagePath)){
            Files.createDirectories(uploadsImagePath);
        }
        Path filePath = uploadsImagePath.resolve(fileName);
        Files.write(filePath, stream.toByteArray());

        return "QRCode saved to: " + uploadFile + fileName;
    }
}
