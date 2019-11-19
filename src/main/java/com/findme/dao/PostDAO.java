package com.findme.dao;

import com.findme.exceptions.SystemException;
import com.findme.models.Post;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Log4j
@Repository
@Transactional
public class PostDAO extends GeneralDAO<Post> {
    private static final String getByUserPage = "SELECT * FROM POSTS WHERE USER_PAGE_ID = :userPageId ORDER BY DATE_POSTED DESC;";
    private static final String getByUserPostedId = "SELECT * FROM POSTS WHERE USER_PAGE_ID = :userPageId AND USER_POSTED_ID = :userPostedId ORDER BY DATE_POSTED DESC;";
    private static final String getByFriends = "SELECT * FROM POSTS WHERE USER_PAGE_ID = :userPageId  AND USER_POSTED_ID != :userPostedId ORDER BY DATE_POSTED DESC;";
    private static final String getFirst20News = "SELECT P.* FROM POSTS P INNER JOIN RELATIONSHIPS R ON " +
            "((P.USER_POSTED_ID = R.USER_FROM_ID AND R.USER_TO_ID = :userId AND STATUS = 'FRIENDS') OR (P.USER_POSTED_ID = R.USER_TO_ID AND R.USER_FROM_ID = :userId AND STATUS = 'FRIENDS')) " +
            "ORDER BY DATE_POSTED DESC LIMIT 20;";
    private static final String getNext20News = "SELECT P.* FROM POSTS P INNER JOIN RELATIONSHIPS R ON " +
            "((P.USER_POSTED_ID = R.USER_FROM_ID AND R.USER_TO_ID = :userId AND STATUS = 'FRIENDS') OR (P.USER_POSTED_ID = R.USER_TO_ID AND R.USER_FROM_ID = :userId AND STATUS = 'FRIENDS')) " +
            "ORDER BY DATE_POSTED DESC OFFSET :lastIndex ROWS FETCH NEXT 20 ROWS ONLY;";

    public PostDAO() {
        settClass(Post.class);
    }

    @PersistenceContext
    private EntityManager entityManager;

    public List<Post> getByPage(Long pageId) {
        log.info("PostDAO getByPage method. Getting posts by page " + pageId);
        return entityManager.createNativeQuery(getByUserPage, Post.class)
                .setParameter("userPageId", pageId)
                .getResultList();
    }

    public List<Post> getByUserPostedId(Long pageId, Long userPostedId) {
        log.info("PostDAO getByUserPostedId method. Getting posts by page " + pageId + " and user posted id " + userPostedId);
        return entityManager.createNativeQuery(getByUserPostedId, Post.class)
                .setParameter("userPageId", pageId)
                .setParameter("userPostedId", userPostedId)
                .getResultList();
    }

    public List<Post> getByFriends(Long pageId) {
        log.info("PostDAO getByFriends method. Getting posts by friends of user " + pageId);
        return entityManager.createNativeQuery(getByFriends, Post.class)
                .setParameter("userPageId", pageId)
                .setParameter("userPostedId", pageId)
                .getResultList();
    }

    public List<Post> getFirst20News(Long userID) {
        log.info("PostDAO getFirst20News method. Getting news for " + userID);
        return entityManager.createNativeQuery(getFirst20News, Post.class)
                .setParameter("userID", userID)
                .getResultList();
    }

    public List<Post> getPostsBatch(Long userID, Long indexOfLastNews) {
        log.info("PostDAO getNext20News method. Getting news for " + userID);
        return entityManager.createNativeQuery(getNext20News, Post.class)
                .setParameter("userId", userID)
                .setParameter("lastIndex", indexOfLastNews)
                .getResultList();
    }

    @Override
    public Post get(Long id) throws SystemException {
        return super.get(id);
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
    public void delete(Long id) throws SystemException {
        super.delete(id);
    }
}
