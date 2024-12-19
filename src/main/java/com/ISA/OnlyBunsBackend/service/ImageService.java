package com.ISA.OnlyBunsBackend.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService  {

    String saveImageToStorage(String uploadDirectory, MultipartFile imageFile) throws IOException;

    public byte[] getImage(String imageDirectory, String imageName) throws  IOException;
}