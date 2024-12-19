package com.ISA.OnlyBunsBackend.scheduled;

import com.ISA.OnlyBunsBackend.model.Post;
import com.ISA.OnlyBunsBackend.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import net.coobird.thumbnailator.Thumbnails;

@Component
public class ImageCompression {

    @Autowired
    private PostRepository bunnyPostRepository;

    // Schedule to run every 5 minutes
    @Scheduled(fixedRate = 300000)
    public void compressImages() {
        String uploadDirectory = "src/main/resources/static/post.images/";
        LocalDateTime date = LocalDateTime.now().minusMonths(1);

        List<Post> posts = bunnyPostRepository.findAll();
        for (Post post : posts) {
            if (date.isAfter(post.getTimeOfPublishing()) && !post.getImage().contains("compressed")) {
                processImage(post.getImage());
                post.setImage("compressed_" + post.getImage());
                bunnyPostRepository.save(post);
            }
        }
    }

    public void processImage(String path) {
        String uploadDirectory = "src/main/resources/static/post.images/";
        try {
            Thumbnails.of(new File(uploadDirectory, path))
                    .scale(0.5)
                    .toFile(new File(uploadDirectory, "compressed_" + path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}