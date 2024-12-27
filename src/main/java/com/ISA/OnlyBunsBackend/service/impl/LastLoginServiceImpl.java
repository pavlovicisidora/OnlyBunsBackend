package com.ISA.OnlyBunsBackend.service.impl;

import com.ISA.OnlyBunsBackend.model.LastLogin;
import com.ISA.OnlyBunsBackend.model.User;
import com.ISA.OnlyBunsBackend.repository.CommentRepository;
import com.ISA.OnlyBunsBackend.repository.LastLoginRepository;
import com.ISA.OnlyBunsBackend.repository.PostRepository;
import com.ISA.OnlyBunsBackend.repository.UserRepository;
import com.ISA.OnlyBunsBackend.service.LastLoginService;
import com.ISA.OnlyBunsBackend.service.PostService;
import com.ISA.OnlyBunsBackend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LastLoginServiceImpl implements LastLoginService {
    @Autowired
    private LastLoginRepository lastLoginRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;


    @Override
    public void updateLastLoginInfo(int userId) {
        Optional<User> currentUser = userRepository.findById(userId);

        if(currentUser.isPresent()) {
            LocalDateTime currentTime = LocalDateTime.now();
            int totalFollowers = currentUser.get().getFollowersNum();
            int totalLikes = postRepository.getUserPostsLikesCount(userId);
            int totalComments = postRepository.getUserPostsCommentsCount(userId);

            LastLogin lastLogin = lastLoginRepository.findByUserId(userId);

            if (lastLogin != null) {
                lastLogin.setLastLoginTime(currentTime);
                lastLogin.setTotalComments(totalComments);
                lastLogin.setTotalLikes(totalLikes);
                lastLogin.setTotalFollowers(totalFollowers);

                lastLoginRepository.save(lastLogin);

            } else {
                lastLogin = new LastLogin();
                lastLogin.setTotalFollowers(totalFollowers);
                lastLogin.setTotalComments(totalComments);
                lastLogin.setTotalLikes(totalLikes);
                lastLogin.setLastLoginTime(currentTime);
                lastLogin.setUser(currentUser.get());

                lastLoginRepository.save(lastLogin);

            }
        } else{
            throw new EntityNotFoundException("User not found");
        }
    }

    @Override
    public List<LastLogin> getAll() {
        return lastLoginRepository.findAll();
    }

    @Override
    public LastLogin findByUserId(int userId) {
        return lastLoginRepository.findByUserId(userId);
    }
}
