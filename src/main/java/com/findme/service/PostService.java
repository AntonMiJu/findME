package com.findme.service;

import com.findme.dao.PostDAO;
import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.ForbiddenException;
import com.findme.exceptions.NotFoundException;
import com.findme.exceptions.SystemException;
import com.findme.models.Post;
import com.findme.models.RelationshipStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostService {
    private PostDAO postDAO;
    private RelationshipService relationshipService;

    @Autowired
    public PostService(PostDAO postDAO, RelationshipService relationshipService) {
        this.postDAO = postDAO;
        this.relationshipService = relationshipService;
    }

    public List<Post> getByPage(Long pageId) throws NotFoundException{
        if (postDAO.getByPage(pageId) == null)
            throw new NotFoundException("404: Posts not found");
        return postDAO.getByPage(pageId);
    }

    public List<Post> getByUserPostedId(Long pageId, Long userPostedId) throws NotFoundException{
        if (postDAO.getByPage(pageId) == null)
            throw new NotFoundException("404: Posts not found");
        return postDAO.getByUserPostedId(pageId, userPostedId);
    }

    public List<Post> getByFriends(Long pageId) throws NotFoundException{
        if (postDAO.getByPage(pageId) == null)
            throw new NotFoundException("404: Posts not found");
        return postDAO.getByFriends(pageId);
    }

    public Post get(Long id) throws NotFoundException, SystemException {
        return postDAO.get(id, Post.class);
    }

    public Post save(Post post) throws SystemException, ForbiddenException, BadRequestException {
        if (relationshipService.get(post.getUserPosted().getId(), post.getUserPagePosted().getId()) == null || !relationshipService.get(post.getUserPosted().getId(), post.getUserPagePosted().getId()).getStatus().equals(RelationshipStatus.FRIENDS))
            throw new ForbiddenException("403: You can post on this page");
        if (post.getMessage().length() > 200 || post.getMessage().contains("http://") || post.getMessage().contains("https://"))
            throw new BadRequestException("400: Bad message format");
        post.setDatePosted(new Date());
        return postDAO.save(post);
    }

    public Post update(Post post) throws SystemException {
        return postDAO.update(post);
    }

    public void delete(Long id) throws SystemException {
        postDAO.delete(id, Post.class);
    }
}
