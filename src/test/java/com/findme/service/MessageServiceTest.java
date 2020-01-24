package com.findme.service;

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
public class MessageServiceTest {
    @Autowired
    private MessageService messageService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void get() {
    }

    @Test
    public void readMessage() {
    }

    @Test
    public void writeMessage() {
    }

    @Test
    public void edit() {
    }

    @Test
    public void delete() {
    }
}