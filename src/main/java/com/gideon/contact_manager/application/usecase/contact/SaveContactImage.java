package com.gideon.contact_manager.application.usecase.contact;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SaveContactImage {

    protected static String saveContactImage(MultipartFile file) {
        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get("uploads/contact-images", fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());

            return filePath.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error saving contact image", e);
        }
    }
}
