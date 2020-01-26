package com.findme.service;

import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.SystemException;
import com.findme.models.Relationship;
import com.findme.models.RelationshipStatus;
import org.junit.*;
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

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getNotExist() throws SystemException {
        thrown.expect(SystemException.class);

        relationshipService.get(Long.parseLong("5"), Long.parseLong("2"));
    }

    @Test
    public void getSuccessful() throws SystemException {
        Relationship relationship = relationshipService.get(Long.parseLong("1"), Long.parseLong("2"));

        Assert.assertEquals(RelationshipStatus.FRIENDS, relationship.getStatus());
    }

    @Test
    public void getIncomeRequestsIdNotInBD() throws SystemException {
        thrown.expect(SystemException.class);

        relationshipService.getIncomeRequests(Long.parseLong("1000000"));
    }

    @Test
    public void getIncomeRequestsSuccessful() throws SystemException {
        long i = 0;
        for (Relationship re : relationshipService.getIncomeRequests(Long.parseLong("1"))) {
            if (!re.getStatus().equals(RelationshipStatus.REQUESTED) &&
                    re.getUserToId().getId().equals(Long.parseLong("1")))
                i++;
        }

        Assert.assertEquals(0, i);
    }

    @Test
    public void getOutcomeRequestsIdNotInBD() throws SystemException {
        thrown.expect(SystemException.class);

        relationshipService.getOutcomeRequests(Long.parseLong("1000000"));
    }

    @Test
    public void getOutcomeRequestsSuccessful() throws SystemException {
        long i = 0;
        for (Relationship re : relationshipService.getOutcomeRequests(Long.parseLong("1"))) {
            if (!re.getStatus().equals(RelationshipStatus.REQUESTED) &&
                    re.getUserFromId().getId().equals(Long.parseLong("1")))
                i++;
        }

        Assert.assertEquals(0, i);
    }

    @Ignore
    @Test
    public void save() {
    }

    @Test
    public void updateRelationshipThatDoesntExist() throws BadRequestException, SystemException {
        thrown.expect(BadRequestException.class);

        relationshipService.update(Long.parseLong("1"), Long.parseLong("1"), "DELETED");
    }

    @Test
    public void updateWrongStatus() throws BadRequestException, SystemException {
        thrown.expect(BadRequestException.class);

        relationshipService.update(Long.parseLong("1"), Long.parseLong("2"), "REJECTED");
    }

    @Test
    public void update() throws BadRequestException, SystemException {
        Relationship relationship = relationshipService.update(Long.parseLong("1"), Long.parseLong("3"), "REJECTED");

        Assert.assertEquals(RelationshipStatus.REJECTED, relationship.getStatus());
    }
}