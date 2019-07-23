package com.findme.service;

import com.findme.dao.PostDAO;
import com.findme.exceptions.NotFoundException;
import com.findme.exceptions.SystemException;
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

    public Post get(Long id) throws NotFoundException, SystemException {
        return postDAO.get(id, Post.class);
    }

    public Post save(Post post) throws SystemException {
        return postDAO.save(post);
    }

    public Post update(Post post) throws SystemException {
        return postDAO.update(post);
    }

    public void delete(Long id) throws SystemException {
        postDAO.delete(id, Post.class);
    }
}
