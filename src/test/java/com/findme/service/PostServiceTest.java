package com.findme.service;

import com.findme.exceptions.BadRequestException;
import com.findme.models.Post;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.findme.config.TestBeanConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestBeanConfig.class)
@WebAppConfiguration
@Transactional
public class PostServiceTest {
    @Autowired
    private PostService postService;

    private Post firstPost;

    @Before
    public void setUp() throws Exception {
        firstPost = postService.get(Long.parseLong("1"));
    }

    @Test
    public void getByPage() throws BadRequestException {
//        Post post = postService.getByPage(Long.parseLong("1"));
    }

    @Test
    public void getFirst20News() {
    }

    @Test
    public void getPostsBatch() {
    }

    @Test
    public void getByUserPostedId() {
    }

    @Test
    public void getByFriends() {
    }

    @Test
    public void get() {
    }

    @Test
    public void save() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}