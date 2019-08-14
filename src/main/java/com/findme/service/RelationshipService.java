package com.findme.service;

import com.findme.dao.RelationshipDAO;
import com.findme.dao.UserDAO;
import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.NotFoundException;
import com.findme.exceptions.SystemException;
import com.findme.models.Relationship;
import com.findme.models.RelationshipStatus;
import com.findme.models.User;
import com.findme.models.ValidateDate;
import com.findme.service.validate.MaxOutcomeRequests;
import com.findme.service.validate.MaxQuantityOfFriends;
import com.findme.service.validate.MinTimeOfFriendship;
import com.findme.service.validate.ValidateChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
        validateSave(userFromId, userToId);

        Relationship relationship = new Relationship();
        relationship.setUserToId(userDAO.get(userFromId, User.class));
        relationship.setUserToId(userDAO.get(userToId, User.class));
        relationship.setStatus(RelationshipStatus.REQUESTED);

        return relationshipDAO.save(relationship);
    }

    public Relationship update(Long userFromId, Long userToId, String status) throws SystemException, BadRequestException{
        if (userFromId.equals(userToId) || relationshipDAO.get(userFromId, userToId).getStatus().toString().equals(status))
            throw new BadRequestException("400: Bad update logic.");

        Relationship relationship = relationshipDAO.get(userFromId, userToId);
        relationship.setStatus(RelationshipStatus.valueOf(status));
        if (status.equals(RelationshipStatus.FRIENDS.toString()))
            relationship.setStartOfRelationships(new Date());

        return relationshipDAO.update(relationship);
    }

    public Relationship delete(Long userFromId, Long userToId) throws BadRequestException, SystemException {
        validateDelete(userFromId, userToId);

        Relationship relationship = relationshipDAO.get(userFromId, userToId);
        relationship.setStatus(RelationshipStatus.DELETED);
        relationship.setStartOfRelationships(null);

        return relationshipDAO.update(relationship);
    }

    private void validateSave(Long userFromId, Long userToId) throws BadRequestException, SystemException{
        if (userFromId.equals(userToId) || relationshipDAO.get(userFromId, userToId) != null)
            throw new BadRequestException("400: Bad logic.");

        ValidateChain chain = new MaxOutcomeRequests();
        chain.setNextChain(new MaxQuantityOfFriends());
        ValidateDate date = new ValidateDate();

        date.setQuantityOfFriendsFrom(relationshipDAO.getFriendsList(userFromId).size());
        date.setQuantityOfFriendsTo(relationshipDAO.getFriendsList(userToId).size());
        date.setQuantityOfOutcomeRequests(relationshipDAO.getOutcomeRequests(userFromId).size());

        chain.validate(date);
    }

    private void validateDelete(Long userFromId, Long userToId) throws BadRequestException {
        Relationship relationship = relationshipDAO.get(userFromId, userToId);
        if (userFromId.equals(userToId) || !relationship.getStatus().equals(RelationshipStatus.FRIENDS))
            throw new BadRequestException("400: Bad delete logic.");

        ValidateChain chain = new MinTimeOfFriendship();
        ValidateDate date = new ValidateDate();

        long differenceInDays = new Date().getTime() - relationship.getStartOfRelationships().getTime();
        date.setDaysFriends((int) (differenceInDays / (24 * 60 * 60 * 1000)));

        chain.validate(date);
    }
}
