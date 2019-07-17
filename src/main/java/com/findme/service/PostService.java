package com.findme.service;

import com.findme.dao.PostDAO;
import com.findme.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private PostDAO postDAO;

    @Autowired
    public PostService(PostDAO postDAO) {
        this.postDAO = postDAO;
    }

    public Post get(Long id) {
        return postDAO.get(id, Post.class);
    }

    public void save(Post post) {
        postDAO.save(post);
    }

    public Post update(Post post) {
        return postDAO.update(post);
    }

    public void delete(Long id) {
        postDAO.delete(id, Post.class);
    }
}
