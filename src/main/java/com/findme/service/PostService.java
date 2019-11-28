package com.findme.service;

import com.findme.dao.PostDAO;
import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.ForbiddenException;
import com.findme.exceptions.SystemException;
import com.findme.models.Post;
import com.findme.models.RelationshipStatus;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Log4j
@Service
public class PostService {
    private PostDAO postDAO;
    private RelationshipService relationshipService;

    @Autowired
    public PostService(PostDAO postDAO, RelationshipService relationshipService) {
        this.postDAO = postDAO;
        this.relationshipService = relationshipService;
    }

    public List<Post> getByPage(Long pageId) throws BadRequestException {
        log.info("PostService getByPage method");
        if (postDAO.getByPage(pageId) == null) {
            log.error("Posts not found for " + pageId);
            throw new BadRequestException("400: Posts not found");
        }
        return postDAO.getByPage(pageId);
    }

    public List<Post> getFirst20News(Long userID) {
        log.info("PostService getFirst20News method");
        return postDAO.getFirst20News(userID);
    }

    public List<Post> getPostsBatch(Long userID, Long indexOfLastNews) {
        log.info("PostService getNext20News method");
        indexOfLastNews += indexOfLastNews.longValue() + 20;
        return postDAO.getPostsBatch(userID, indexOfLastNews);
    }

    public List<Post> getByUserPostedId(Long pageId, Long userPostedId) throws BadRequestException {
        log.info("PostService getByUserPostedId method");
        if (postDAO.getByPage(pageId) == null) {
            log.error("Posts of user " + userPostedId + " not found for " + pageId);
            throw new BadRequestException("400: Posts not found");
        }
        return postDAO.getByUserPostedId(pageId, userPostedId);
    }

    public List<Post> getByFriends(Long pageId) throws BadRequestException {
        log.info("PostService getByFriends method");
        if (postDAO.getByPage(pageId) == null) {
            log.error("Posts of friends not found for " + pageId);
            throw new BadRequestException("400: Posts not found");
        }
        return postDAO.getByFriends(pageId);
    }

    public Post get(Long id) throws SystemException {
        log.info("PostService get method");
        return postDAO.get(id);
    }

    public Post save(Post post) throws SystemException, ForbiddenException, BadRequestException {
        log.info("PostService save method");
        if (relationshipService.get(post.getUserPosted().getId(), post.getUserPagePosted().getId()) == null ||
                !relationshipService.get(post.getUserPosted().getId(), post.getUserPagePosted().getId()).getStatus().equals(RelationshipStatus.FRIENDS)) {
            log.error("You can post on this page");
            throw new ForbiddenException("403: You can post on this page");
        }
        validateMessage(post);
        post.setDatePosted(new Date());
        return postDAO.save(post);
    }

    public Post update(Long userId, Post post) throws SystemException, ForbiddenException, BadRequestException {
        log.info("PostService update method");
        if (userId.equals(post.getUserPosted().getId())){
            log.error("You can`t update this post.");
            throw new ForbiddenException("403: You can`t update this post.");
        }
        validateMessage(post);
        return postDAO.update(post);
    }

    public void delete(Long userId, Long postId) throws SystemException, ForbiddenException {
        log.info("PostService delete method");
        Post post = postDAO.get(postId);
        if (!userId.equals(post.getUserPosted().getId()) && !userId.equals(post.getUserPagePosted().getId())){
            log.error("You can`t delete this post.");
            throw new ForbiddenException("403: You can`t delete this post.");
        }
        postDAO.delete(postId);
    }

    private void validateMessage(Post post) throws BadRequestException{
        if (post.getMessage().length() > 200 || post.getMessage().contains("http://") || post.getMessage().contains("https://")) {
            log.error("Bad message format");
            throw new BadRequestException("400: Bad message format");
        }
    }
}
