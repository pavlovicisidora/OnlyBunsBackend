package com.ISA.OnlyBunsBackend.controller;


import com.ISA.OnlyBunsBackend.dto.PostViewDTO;
import com.ISA.OnlyBunsBackend.dto.UsersViewDTO;
import com.ISA.OnlyBunsBackend.service.AdvertiseService;
import com.ISA.OnlyBunsBackend.service.PostService;
import com.ISA.OnlyBunsBackend.service.UserService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "api/advertise")
public class AdvertiseController {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private AdvertiseService advertiseService;


    @PostMapping("/markForAd/{postId}")
    public ResponseEntity<Void>  markPostForAd( @PathVariable Integer postId) throws IOException {
        PostViewDTO postAd = postService.getPostById(postId);
        UsersViewDTO user = userService.getUser(postAd.getUserId());


        advertiseService.sendPostForAd(postAd.getDescription(), user.getUsername(), postAd.getTimeOfPublishing());
        return ResponseEntity.ok().build();
    }

}
