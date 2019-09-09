package com.findme.controller;

import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.ForbiddenException;
import com.findme.exceptions.NotFoundException;
import com.findme.exceptions.SystemException;
import com.findme.models.Post;
import com.findme.models.User;
import com.findme.service.PostService;
import com.findme.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.interceptor.Interceptors;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Interceptors(ValidateInterceptor.class)
public class PostController {
    private static final Logger log = Logger.getLogger(PostController.class);
    private PostService postService;
    private UserService userService;

    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @RequestMapping(path = "/user/{userId}/create_post", method = RequestMethod.POST)
    public String createPost(HttpSession session, Model model, @PathVariable Long userId) {
        log.info("PostController createPost method.");
        User user = null;
        try {
            Post post = new Post();
            //TODO set message, location, tagged
            post.setUserPosted((User) session.getAttribute("user"));
            user = userService.get(userId);
            post.setUserPagePosted(user);
            postService.save(post);
        } catch (SystemException e) {
            return "systemException";
        } catch (NotFoundException e) {
            return "notFoundException";
        } catch (ForbiddenException e) {
            return "forbiddenException";
        } catch (BadRequestException e) {
            return "badRequestException";
        }
        model.addAttribute("user", user);
        return "profile";
    }

    @RequestMapping(path = "/post/{userId}", method = RequestMethod.GET)
    public String getPosts(HttpSession session, Model model, @PathVariable Long userId){
        log.info("PostController getPosts method.");
        List<Post> posts;
        try {
            posts = postService.getByPage(userId);
        } catch (NotFoundException e){
            return "notFoundException";
        }
        model.addAttribute("posts", posts);
        return "";
    }

    @RequestMapping(path = "/post/{userId}/by_friends", method = RequestMethod.GET)
    public String getPostsByFriends(HttpSession session, Model model, @PathVariable Long userId){
        log.info("PostController getPostsByFriends method.");
        List<Post> posts;
        try {
            posts = postService.getByFriends(userId);
        } catch (NotFoundException e){
            return "notFoundException";
        }
        model.addAttribute("posts", posts);
        return "";
    }

    @RequestMapping(path = "/post/{userId}/owner_posts", method = RequestMethod.GET)
    public String getPostsOnlyOwnerOfPage(HttpSession session, Model model, @PathVariable Long userId){
        log.info("PostController getPostsOnlyOwnerOfPage method.");
        List<Post> posts;
        try {
            posts = postService.getByUserPostedId(userId, userId);
        } catch (NotFoundException e){
            return "notFoundException";
        }
        model.addAttribute("posts", posts);
        return "";
    }

    @RequestMapping(path = "/post/{userId}/by_user_id", method = RequestMethod.GET)
    public String getPosts(HttpSession session, Model model, @PathVariable Long userId, @RequestParam(name = "password") Long userPostedId){
        log.info("PostController getPosts method.");
        List<Post> posts;
        try {
            posts = postService.getByUserPostedId(userId, userPostedId);
        } catch (NotFoundException e){
            return "notFoundException";
        }
        model.addAttribute("posts", posts);
        return "";
    }

    @RequestMapping(path = "/feed", method = RequestMethod.GET)
    public String getNews(HttpSession session, Model model){
        log.info("PostController getNews method.");
        model.addAttribute(postService.getFirst20News(((User)session.getAttribute("user")).getId()));
        return "feed";
    }
}
