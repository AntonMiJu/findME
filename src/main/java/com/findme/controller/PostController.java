package com.findme.controller;

import com.findme.exceptions.BadRequestException;
import com.findme.interceptor.ValidateInterceptor;
import com.findme.models.Post;
import com.findme.models.User;
import com.findme.service.PostService;
import com.findme.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.interceptor.Interceptors;
import javax.servlet.http.HttpSession;

@Log4j
@Controller
@Interceptors(ValidateInterceptor.class)
public class PostController {
    private PostService postService;
    private UserService userService;

    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @RequestMapping(path = "/user/{userId}/create_post", method = RequestMethod.POST)
    public String createPost(HttpSession session, Model model,@ModelAttribute Post post, @PathVariable Long userId) throws Exception {
        log.info("PostController createPost method.");
        //TODO set message, location, tagged
        post.setUserPosted((User) session.getAttribute("user"));
        post.setUserPagePosted(userService.get(userId));
        postService.save(post);
        model.addAttribute("user", userService.get(userId));
        return "profile";
    }

    @RequestMapping(path = "/post/{userId}", method = RequestMethod.GET)
    public String getPosts(HttpSession session, Model model, @PathVariable Long userId) throws BadRequestException {
        log.info("PostController getPosts method.");
        model.addAttribute("posts", postService.getByPage(userId));
        return "posts";
    }

    @RequestMapping(path = "/post/{userId}/by_friends", method = RequestMethod.GET)
    public String getPostsByFriends(HttpSession session, Model model, @PathVariable Long userId) throws BadRequestException {
        log.info("PostController getPostsByFriends method.");
        model.addAttribute("posts", postService.getByFriends(userId));
        return "posts";
    }

    @RequestMapping(path = "/post/{userId}/owner_posts", method = RequestMethod.GET)
    public String getPostsOnlyOwnerOfPage(HttpSession session, Model model, @PathVariable Long userId) throws BadRequestException {
        log.info("PostController getPostsOnlyOwnerOfPage method.");
        model.addAttribute("posts", postService.getByUserPostedId(userId, userId));
        return "posts";
    }

    @RequestMapping(path = "/post/{userId}/by_user_id", method = RequestMethod.GET)
    public String getPostsByPostedId(HttpSession session, Model model, @PathVariable Long userId, @RequestParam(name = "userPostedId") Long userPostedId) throws BadRequestException {
        log.info("PostController getPosts method.");
        model.addAttribute("posts", postService.getByUserPostedId(userId, userPostedId));
        return "posts";
    }

    @RequestMapping(path = "/feed", method = RequestMethod.GET)
    public String getNews(HttpSession session, Model model) {
        log.info("PostController getNews method.");
        model.addAttribute(postService.getFirst20News(((User) session.getAttribute("user")).getId()));
        return "feed";
    }

    @RequestMapping(path = "/next20feed", method = RequestMethod.GET)
    public String getPostsBatch(HttpSession session, Model model, Long indexOfLastNews) {
        log.info("PostController getNext20News method.");
        model.addAttribute(postService.getPostsBatch(((User) session.getAttribute("user")).getId(), indexOfLastNews));
        return "feed";
    }
}
