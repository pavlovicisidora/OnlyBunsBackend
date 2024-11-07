package com.ISA.OnlyBunsBackend.repository;
import com.ISA.OnlyBunsBackend.model.Comment;
import com.ISA.OnlyBunsBackend.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRespository extends JpaRepository<Post,Integer> {

  //  @Query("select t from Teacher t join fetch t.courses e where t.id =?1")
    @Query(value = "SELECT p FROM Post p WHERE p.id = ?1")
    @Modifying
    public Post findOneById(Integer id);

    public Page<Post> findAll(Pageable pageable);

    @Query("SELECT p.comments FROM Post p WHERE p.id = ?1")
    public List<Comment> getAllPostComment(Integer id);

    @Query("SELECT SIZE(p.userLikes) FROM Post p WHERE p.id = :postId")
    public Integer  getAllPostLikes(Integer id);



}
