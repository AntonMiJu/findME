package com.findme.service;

import com.findme.exceptions.SystemException;
import com.findme.models.Relationship;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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
public class RelationshipServiceTest {
    @Autowired
    private RelationshipService relationshipService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Relationship friends;
    private Relationship requested;
    private Relationship rejected;
    private Relationship canceled;
    private Relationship deleted;

    @Before
    public void setUp() throws Exception {
        friends = relationshipService.get(Long.parseLong("1"), Long.parseLong("2"));
        requested = relationshipService.get(Long.parseLong("1"), Long.parseLong("3"));
        rejected = relationshipService.get(Long.parseLong("2"), Long.parseLong("4"));
        canceled = relationshipService.get(Long.parseLong("2"), Long.parseLong("3"));
        deleted = relationshipService.get(Long.parseLong("4"), Long.parseLong("3"));
    }

    @Test
    public void getNotExist() throws SystemException{
        thrown.expect(SystemException.class);

        relationshipService.get(Long.parseLong("5"), Long.parseLong("2"));
    }

    @Test
    public void getSuccessful() throws SystemException{
        Relationship relationship = relationshipService.get(Long.parseLong("1"), Long.parseLong("2"));

        Assert.assertEquals(friends, relationship);
    }

    @Test
    public void getIncomeRequests() {
    }

    @Test
    public void getOutcomeRequests() {
    }

    @Test
    public void save() {
    }

    @Test
    public void update() {
    }
}