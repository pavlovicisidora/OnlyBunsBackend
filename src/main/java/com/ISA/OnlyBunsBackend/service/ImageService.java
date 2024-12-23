package com.ISA.OnlyBunsBackend.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService  {

    String saveImageToStorage(String uploadDirectory, MultipartFile imageFile) throws IOException;
    byte[] getImage(String imageDirectory, String imageName) throws  IOException;
}