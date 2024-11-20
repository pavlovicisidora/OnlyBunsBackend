package com.ISA.OnlyBunsBackend.controller;

import com.ISA.OnlyBunsBackend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    @Autowired
    private ImageService imageService;


    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile pictureURL) {
        try {
            String imagePath = imageService.saveImageToStorage("/src/main/resources/static/post.images/", pictureURL);
            return ResponseEntity.ok(imagePath); // Vraća putanju slike
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
        }
    }


    @GetMapping
    public ResponseEntity<byte[]> getImage(@RequestParam("filePath") String pictureURL) {
        try {
            byte[] imageBytes = imageService.getImage("static/post.images/", pictureURL);
            if (imageBytes != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "image/jpeg"); // Postavi odgovarajući tip
                return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}