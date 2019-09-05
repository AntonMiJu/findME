package com.findme.dao;

import com.findme.exceptions.NotFoundException;
import com.findme.exceptions.SystemException;
import com.findme.models.Post;
import com.findme.models.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository
@Transactional
public class PostDAO extends GeneralDAO<Post> {
    private static final String getByUserPage = "SELECT * FROM POSTS WHERE USER_PAGE_ID = :userPageId ORDER BY DATE_POSTED DESC;";
    private static final String getByUserPostedId = "SELECT * FROM POSTS WHERE USER_PAGE_ID = :userPageId AND USER_POSTED_ID = :userPostedId ORDER BY DATE_POSTED DESC;";
    private static final String getByFriends = "SELECT * FROM POSTS WHERE USER_PAGE_ID = :userPageId  AND USER_POSTED_ID != :userPostedId ORDER BY DATE_POSTED DESC;";
    private static final String getFirst20News = "SELECT * FROM POSTS P WHERE P.USER_POSTED_ID IN " +
            "(SELECT USER_FROM_ID FROM RELATIONSHIPS R WHERE R.USER_TO_ID = :userId AND R.STATUS = 'FRIENDS') OR " +
            "P.USER_POSTED_ID IN (SELECT USER_TO_ID FROM RELATIONSHIPS WHERE USER_FROM_ID = :userId AND STATUS = 'FRIENDS')" +
            " ORDER BY DATE_POSTED DESC LIMIT 20;";
    private static final String getNext20News = "SELECT * FROM POSTS P WHERE P.USER_POSTED_ID IN " +
            "(SELECT USER_FROM_ID FROM RELATIONSHIPS R WHERE R.USER_TO_ID = :userId AND R.STATUS = 'FRIENDS') OR " +
            "P.USER_POSTED_ID IN (SELECT USER_TO_ID FROM RELATIONSHIPS WHERE USER_FROM_ID = :userId AND STATUS = 'FRIENDS')" +
            "ORDER BY DATE_POSTED DESC OFFSET :lastIndex ROWS FETCH NEXT 20 ROWS ONLY;";

    private RelationshipDAO relationshipDAO;

    @Autowired
    public PostDAO(RelationshipDAO relationshipDAO) {
        this.relationshipDAO = relationshipDAO;
    }

    @PersistenceContext
    private EntityManager entityManager;

    public List<Post> getByPage(Long pageId) {
        return entityManager.createNativeQuery(getByUserPage, Post.class)
                .setParameter("userPageId", pageId)
                .getResultList();
    }

    public List<Post> getByUserPostedId(Long pageId, Long userPostedId) {
        return entityManager.createNativeQuery(getByUserPostedId, Post.class)
                .setParameter("userPageId", pageId)
                .setParameter("userPostedId", userPostedId)
                .getResultList();
    }

    public List<Post> getByFriends(Long pageId) {
        return entityManager.createNativeQuery(getByFriends, Post.class)
                .setParameter("userPageId", pageId)
                .setParameter("userPostedId", pageId)
                .getResultList();
    }

    public List<Post> getFirst20News(Long userID) {
        return entityManager.createNativeQuery(getFirst20News, Post.class)
                .setParameter("userID", userID)
                .getResultList();
    }

    public List<Post> getNext20News(Long userID, Long indexOfLastNews) {
        return entityManager.createNativeQuery(getNext20News, Post.class)
                .setParameter("userId", userID)
                .setParameter("lastIndex", indexOfLastNews)
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
