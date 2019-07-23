package com.findme.dao;

import com.findme.exceptions.NotFoundException;
import com.findme.exceptions.SystemException;
import com.findme.models.Post;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class PostDAO extends GeneralDAO<Post> {
    @Override
    public Post get(Long id, Class<Post> postClass) throws NotFoundException, SystemException {
        return super.get(id, postClass);
    }

    @Override
    public Post save(Post post) throws SystemException {
        return super.save(post);
    }

    @Override
    public Post update(Post post) throws SystemException {
        return super.update(post);
    }

    @Override
    public void delete(Long id, Class<Post> postClass) throws SystemException {
        super.delete(id, postClass);
    }
}
