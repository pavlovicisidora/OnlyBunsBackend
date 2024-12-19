package com.ISA.OnlyBunsBackend.service.impl;

import com.ISA.OnlyBunsBackend.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static java.nio.file.Paths.get;

@Service
public class ImageServiceImpl implements ImageService {

    // Save image in a local directory
    public String saveImageToStorage(String uploadDirectory, MultipartFile imageFile) throws IOException {
        String uniqueFileName = imageFile.getOriginalFilename();
        Path uploadPath = get(uploadDirectory);
        Path filePath = uploadPath.resolve(uniqueFileName);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }

    // Retrieve an image
    public byte[] getImage(String imageDirectory, String imageName) throws IOException {
        Path imagePath = get(imageDirectory, imageName);

        if (Files.exists(imagePath)) {
            byte[] imageBytes = Files.readAllBytes(imagePath);
            return imageBytes;
        }
        return null; // Handle missing images
    }
}