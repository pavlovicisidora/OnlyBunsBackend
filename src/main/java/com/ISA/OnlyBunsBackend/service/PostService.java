package com.ISA.OnlyBunsBackend.service;
import com.ISA.OnlyBunsBackend.model.Post;
import com.ISA.OnlyBunsBackend.repository.PostRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRespository postRespository;

    public Post findOneByIndex(Integer id){
        return postRespository.findOneByIndex(id);
    }
    public Post save(Post post){
        return postRespository.save(post);
    }
    public List<Post> findAll(){
        return postRespository.findAll();
    }
    public boolean delete(Integer id){
        return postRespository.deleteById(id);
    }


}
