package com.ISA.OnlyBunsBackend.service;

import com.ISA.OnlyBunsBackend.dto.PostDTO;
import com.ISA.OnlyBunsBackend.model.Comment;
import com.ISA.OnlyBunsBackend.dto.PostViewDTO;
import com.ISA.OnlyBunsBackend.model.Post;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface PostService {
    List<PostViewDTO> getAllPosts();
    PostViewDTO likePost(Integer postId, Integer userId);
    Comment addComment(Integer postId, Integer userId, String content);
    PostViewDTO updatePost(Integer postId, Integer userId, String newDescription, String newImage);
    void deletePost(Integer postId, Integer userId);
    boolean isLiked(Integer postId, Integer userId);
    PostDTO createPost(PostDTO post);
    PostViewDTO getPostById(int id);
    List<PostViewDTO> getPostsByFollowedUsers(Integer userId);
    List<PostViewDTO> getAllPostsByUserId(Integer userId);

   int getAllPostsCount();
   long getPostsCountInLastMonth();
   @Cacheable(cacheNames = "allPostsInLast7Days")
   List<PostViewDTO> getTop5MostLikedPostsInLast7Days();

    @Cacheable(cacheNames = "allPostsOfAllTimes")
   List<PostViewDTO> getTop10MostLikedPostsOfAllTime();

    @CacheEvict(cacheNames = {"allPostsOfAllTimes", "allPostsInLast7Days"}, allEntries = true)
    void removeFromCache();
}
