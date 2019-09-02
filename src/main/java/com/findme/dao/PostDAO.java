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
    private static final String getByUserPage = "SELECT * FROM POSTS WHERE USER_PAGE_ID = ? ORDER BY DATE_POSTED DESC;";
    private static final String getByUserPostedId = "SELECT * FROM POSTS WHERE USER_PAGE_ID = ? AND USER_POSTED_ID = ? ORDER BY DATE_POSTED DESC;";
    private static final String getByFriends = "SELECT * FROM POSTS WHERE USER_PAGE_ID = ? AND USER_POSTED_ID != ? ORDER BY DATE_POSTED DESC;";
    private static final String getFirst20News = "SELECT * FROM POSTS WHERE USER_POSTED_ID = ? ORDER BY DATE_POSTED DESC LIMIT 20;";
    private static final String getNext20News = "SELECT * FROM POSTS WHERE USER_POSTED_ID = ? ORDER BY DATE_POSTED DESC OFFSET ? ROWS FETCH NEXT 20 ROWS ONLY;";

    private RelationshipDAO relationshipDAO;

    @Autowired
    public PostDAO(RelationshipDAO relationshipDAO) {
        this.relationshipDAO = relationshipDAO;
    }

    @PersistenceContext
    private EntityManager entityManager;

    public List<Post> getByPage(Long pageId) {
        return entityManager.createNativeQuery(getByUserPage, Post.class)
                .setParameter(1, pageId)
                .getResultList();
    }

    public List<Post> getByUserPostedId(Long pageId, Long userPostedId) {
        return entityManager.createNativeQuery(getByUserPostedId, Post.class)
                .setParameter(1, pageId)
                .setParameter(2, userPostedId)
                .getResultList();
    }

    public List<Post> getByFriends(Long pageId) {
        return entityManager.createNativeQuery(getByFriends, Post.class)
                .setParameter(1, pageId)
                .setParameter(2, pageId)
                .getResultList();
    }

    public List<Post> getFirst20News(Long userID) throws SystemException {
        Long friendID;
        List<Post> posts = new ArrayList<Post>();
        for (Relationship rel : relationshipDAO.getFriendsList(userID)) {
            friendID = rel.getUserFromId().getId().equals(userID) ? rel.getUserFromId().getId() : rel.getUserToId().getId();
            posts.addAll(entityManager.createNativeQuery(getFirst20News, Post.class)
                    .setParameter(1, friendID)
                    .getResultList());
        }
        return posts;
    }

    public List<Post> getNext20News(Long userID, Long indexOfLastNews) throws SystemException {
        Long friendID;
        List<Post> posts = new ArrayList<Post>();
        for (Relationship rel : relationshipDAO.getFriendsList(userID)) {
            friendID = rel.getUserFromId().getId().equals(userID) ? rel.getUserFromId().getId() : rel.getUserToId().getId();
            posts.addAll(entityManager.createNativeQuery(getNext20News, Post.class)
                    .setParameter(1, friendID)
                    .setParameter(2, indexOfLastNews)
                    .getResultList());
        }
        return posts;
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
