package com.findme.service;

import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.SystemException;
import com.findme.models.User;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.findme.config.TestBeanConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestBeanConfig.class)
@WebAppConfiguration
@Transactional
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

        userNotExist = new User();
        userNotExist.setId(Long.parseLong("1000000"));
        userNotExist.setEmail("notExist");
        userNotExist.setPhone("notExist");
        userNotExist.setPassword("notExist");
    }

    @Test
    public void getNotExistUser() throws SystemException {
        User user = userService.get(Long.parseLong("0"));

        Assert.assertNull(user);
    }

    @Test
    public void getSuccessful() throws SystemException {
        User user = userService.get(Long.parseLong("1"));

        Assert.assertEquals(admin, user);
    }

    @Test
    public void loginUserNotFound() throws SystemException, BadRequestException {
        thrown.expect(BadRequestException.class);

        userService.login(userNotExist.getEmail(), userNotExist.getPassword());
    }

    @Test
    public void loginSuccessful() throws BadRequestException, SystemException {
        User user = userService.login(admin.getEmail(), admin.getPassword());

        Assert.assertEquals(admin, user);
    }

    @Test
    public void saveExistedUser() throws BadRequestException, SystemException {
        thrown.expect(BadRequestException.class);

        userService.save(admin);
    }

    @Test
    public void save() throws SystemException, BadRequestException {
        User userForSave = new User();
        userForSave.setEmail("user");
        userForSave.setPhone("000");
        userForSave.setPassword("pass");
        userForSave.setTagsInPosts(null);

        User user = userService.save(userForSave);

        Assert.assertEquals(userForSave, user);
    }

    @Test
    public void updateUserNotExist() throws SystemException {
        thrown.expect(SystemException.class);

        userService.update(userNotExist);
    }

    @Test
    public void updateSuccessful() throws SystemException {
        admin.setDateLastActive(new Date());

        User user = userService.update(admin);

        Assert.assertEquals(admin, user);
    }

    @Test
    public void deleteUserNotExist() throws SystemException {
        thrown.expect(SystemException.class);

        userService.delete(Long.parseLong("0"));
    }
}