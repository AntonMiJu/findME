package com.findme.dao;

import com.findme.exceptions.NotFoundException;
import com.findme.exceptions.SystemException;
import com.findme.models.Post;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class PostDAO extends GeneralDAO<Post> {
    private static final String getByUserPage = "SELECT * FROM POSTS WHERE USER_PAGE_ID = ? ORDER BY DATE_POSTED DESC;";
    private static final String getByUserPostedId = "SELECT * FROM POSTS WHERE USER_PAGE_ID = ? AND USER_POSTED_ID = ? ORDER BY DATE_POSTED DESC;";
    private static final String getByFriends = "SELECT * FROM POSTS WHERE USER_PAGE_ID = ? AND USER_POSTED_ID != ? ORDER BY DATE_POSTED DESC;";

    @PersistenceContext
    private EntityManager entityManager;

    public List<Post> getByPage(Long pageId){
        return entityManager.createNativeQuery(getByUserPage, Post.class)
                .setParameter(1, pageId)
                .getResultList();
    }

    public List<Post> getByUserPostedId(Long pageId, Long userPostedId){
        return entityManager.createNativeQuery(getByUserPostedId, Post.class)
                .setParameter(1, pageId)
                .setParameter(2, userPostedId)
                .getResultList();
    }

    public List<Post> getByFriends(Long pageId){
        return entityManager.createNativeQuery(getByFriends, Post.class)
                .setParameter(1, pageId)
                .setParameter(2, pageId)
                .getResultList();
    }

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
