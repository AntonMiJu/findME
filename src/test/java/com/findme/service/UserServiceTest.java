package com.findme.service;

import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.SystemException;
import com.findme.models.User;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.findme.config.TestBeanConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestBeanConfig.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private User admin;
    private User userNotExist;

    @Before
    public void setUp() throws Exception {
        admin = userService.get(Long.parseLong("1"));
        userNotExist = userService.get(Long.parseLong("0"));
    }

    @Test
    public void loginUserNotFound() throws SystemException, BadRequestException {
        thrown.expect(BadRequestException.class);
        userService.login(userNotExist.getEmail(), userNotExist.getPassword());
    }

    @Ignore
    @Test
    public void login() {
    }

    @Test
    public void saveExistedUser() throws BadRequestException, SystemException {
        thrown.expect(BadRequestException.class);
        userService.save(admin);
    }

    @Ignore
    @Test
    public void save() {
    }

    @Ignore
    @Test
    public void update() {
    }

    @Ignore
    @Test
    public void delete() {
    }
}