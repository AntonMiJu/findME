package com.findme.service;

import com.findme.dao.RelationshipDAO;
import com.findme.dao.UserDAO;
import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.NotFoundException;
import com.findme.exceptions.SystemException;
import com.findme.models.Relationship;
import com.findme.models.RelationshipStatus;
import com.findme.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelationshipService {
    private RelationshipDAO relationshipDAO;
    private UserDAO userDAO;

    @Autowired
    public RelationshipService(RelationshipDAO relationshipDAO, UserDAO userDAO) {
        this.relationshipDAO = relationshipDAO;
        this.userDAO = userDAO;
    }

    public Relationship get(Long userFromId, Long userToId) {
        return relationshipDAO.get(userFromId, userToId);
    }

    public List<Relationship> getIncomeRequests(Long userId) throws SystemException {
        return relationshipDAO.getIncomeRequests(userId);
    }

    public List<Relationship> getOutcomeRequests(Long userId) throws SystemException {
        return relationshipDAO.getOutcomeRequests(userId);
    }

    public Relationship save(Long userFromId, Long userToId) throws SystemException, BadRequestException, NotFoundException {
        if (userFromId.equals(userToId) || relationshipDAO.get(userFromId,userToId) != null)
            throw new BadRequestException("Bad save logic.");
        return relationshipDAO.save(new Relationship(userDAO.get(userFromId, User.class), userDAO.get(userToId, User.class), RelationshipStatus.REQUEST_SENT));
    }

    public Relationship update(Long userFromId, Long userToId, String status) throws SystemException, BadRequestException, NotFoundException {
        if (userFromId.equals(userToId) || relationshipDAO.get(userFromId,userToId).getStatus().toString().equals(status))
            throw new BadRequestException("Bad update logic.");
        return relationshipDAO.update(new Relationship(userDAO.get(userFromId, User.class), userDAO.get(userToId, User.class), RelationshipStatus.valueOf(status)));
    }
}
