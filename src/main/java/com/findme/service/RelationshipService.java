package com.findme.service;

import com.findme.dao.RelationshipDAO;
import com.findme.dao.UserDAO;
import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.NotFoundException;
import com.findme.exceptions.SystemException;
import com.findme.models.Relationship;
import com.findme.models.RelationshipStatus;
import com.findme.models.ValidateDate;
import com.findme.service.validate.MaxOutcomeRequests;
import com.findme.service.validate.MaxQuantityOfFriends;
import com.findme.service.validate.MinTimeOfFriendship;
import com.findme.service.validate.ValidateChain;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Log4j
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
        log.info("RelationshipService get method");
        return relationshipDAO.get(userFromId, userToId);
    }

    public List<Relationship> getIncomeRequests(Long userId) throws SystemException {
        log.info("RelationshipService getIncomeRequests method");
        return relationshipDAO.getIncomeRequests(userId);
    }

    public List<Relationship> getOutcomeRequests(Long userId) throws SystemException {
        log.info("RelationshipService getOutcomeRequests method");
        return relationshipDAO.getOutcomeRequests(userId);
    }

    public Relationship save(Long userFromId, Long userToId) throws SystemException, BadRequestException, NotFoundException {
        log.info("RelationshipService save method");
        if (userFromId.equals(userToId) || relationshipDAO.get(userFromId, userToId) != null){
            log.error("Saving is failed");
            throw new BadRequestException("400: Bad save logic.");
        }
        validateSave(userFromId, userToId);

        Relationship relationship = new Relationship();
        relationship.setUserToId(userDAO.get(userFromId));
        relationship.setUserToId(userDAO.get(userToId));
        relationship.setStatus(RelationshipStatus.REQUESTED);

        return relationshipDAO.save(relationship);
    }

    public Relationship update(Long userFromId, Long userToId, String status) throws SystemException, BadRequestException{
        log.info("RelationshipService update method");
        if (userFromId.equals(userToId) || relationshipDAO.get(userFromId, userToId)==null || relationshipDAO.get(userFromId, userToId).getStatus().toString().equals(status)){
            log.error("Updating is failed");
            throw new BadRequestException("400: Bad update logic.");
        }

        Relationship relationship = relationshipDAO.get(userFromId, userToId);

        validateStatus(relationship.getStatus(), RelationshipStatus.valueOf(status));

        if (status.equals(RelationshipStatus.REQUESTED.toString())){
            validateSave(userFromId, userToId);
        }

        if (status.equals(RelationshipStatus.FRIENDS.toString())){
            validateSave(userFromId, userToId);
            relationship.setStartOfRelationships(new Date());
        }

        if (status.equals(RelationshipStatus.DELETED.toString())){
            validateDelete(userFromId, userToId);
            relationship.setStartOfRelationships(null);
        }

        relationship.setStatus(RelationshipStatus.valueOf(status));
        return relationshipDAO.update(relationship);
    }

    private void validateStatus(RelationshipStatus relStatus, RelationshipStatus status) throws SystemException{
        if (relStatus.equals(RelationshipStatus.REQUESTED) && (status.equals(RelationshipStatus.FRIENDS) ||
                status.equals(RelationshipStatus.REJECTED) || status.equals(RelationshipStatus.CANCELED)))
            return;

        if (relStatus.equals(RelationshipStatus.FRIENDS) && status.equals(RelationshipStatus.DELETED))
            return;

        if ((relStatus.equals(RelationshipStatus.REJECTED) || relStatus.equals(RelationshipStatus.DELETED) ||
                relStatus.equals(RelationshipStatus.CANCELED)) && status.equals(RelationshipStatus.REQUESTED))
            return;

        throw new SystemException("500: Wrong status update.");
    }

    private void validateSave(Long userFromId, Long userToId) throws BadRequestException, SystemException{
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
        if (userFromId.equals(userToId) || !relationship.getStatus().equals(RelationshipStatus.FRIENDS)){
            log.error("Deleting is failed");
            throw new BadRequestException("400: Bad delete logic.");
        }

        ValidateChain chain = new MinTimeOfFriendship();
        ValidateDate date = new ValidateDate();

        long differenceInDays = new Date().getTime() - relationship.getStartOfRelationships().getTime();
        date.setDaysFriends((int) (differenceInDays / (24 * 60 * 60 * 1000)));

        chain.validate(date);
    }
}
