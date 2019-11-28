package com.findme.controller.statusControllers;

import com.findme.interceptor.ValidateInterceptor;
import com.findme.models.Post;
import com.findme.models.User;
import com.findme.service.PostService;
import com.findme.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.interceptor.Interceptors;
import javax.servlet.http.HttpSession;

@Log4j
@RestController
@Interceptors(ValidateInterceptor.class)
public class PostStatusController {
    private UserService userService;
    private PostService postService;

    @Autowired
    public PostStatusController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @RequestMapping(path = "/user/{userId}/create_post", method = RequestMethod.POST)
    public ResponseEntity<String> createPost(HttpSession session, @ModelAttribute Post post, @PathVariable Long userId) throws Exception {
        log.info("PostStatusController createPost method.");
        //TODO set message, location, tagged
        post.setUserPosted((User) session.getAttribute("user"));
        post.setUserPagePosted(userService.get(userId));
        postService.save(post);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/user/{userId}/delete_post", method = RequestMethod.PUT)
    public ResponseEntity<String> updatePost(HttpSession session, @ModelAttribute Post post) throws Exception {
        log.info("PostStatusController updatePost method");
        postService.update(((User) session.getAttribute("user")).getId(), post);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/user/{userId}/delete_post", method = RequestMethod.DELETE)
    public ResponseEntity<String> deletePost(HttpSession session, @RequestParam(name = "postId") Long postId) throws Exception {
        log.info("PostStatusController deletePost method.");
        postService.delete(((User) session.getAttribute("user")).getId(), postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
