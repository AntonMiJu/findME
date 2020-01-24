package com.findme.dao;

import com.findme.exceptions.SystemException;
import com.findme.models.Relationship;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Log4j
@Repository
public class RelationshipDAO extends GeneralDAO<Relationship> {
    private static final String get = "SELECT * FROM RELATIONSHIPS WHERE ( USER_FROM_ID = :userFromId " +
            "AND USER_TO_ID = :userToId ) OR ( USER_FROM_ID = :userToId AND USER_TO_ID = :userFromId );";
    private static final String getIncomeRequests = "SELECT USER_FROM_ID FROM RELATIONSHIPS WHERE USER_TO_ID = :userToId " +
            "AND STATUS = 'REQUEST_SENT'";
    private static final String getOutcomeRequests = "SELECT USER_TO_ID FROM RELATIONSHIPS WHERE USER_FROM_ID = :userFromId " +
            "AND STATUS = 'REQUEST_SENT'";
    private static final String getFriendsList = "SELECT * FROM RELATIONSHIPS WHERE (USER_FROM_ID = :userFromId" +
            " OR USER_TO_ID = :userToId ) AND STATUS = 'FRIENDS'";

    @PersistenceContext
    private EntityManager entityManager;

    public RelationshipDAO() {
        settClass(Relationship.class);
    }

    @Transactional
    public Relationship get(Long userFromId, Long userToId) throws SystemException{
        try {
            log.info("RelationshipDAO get method. Getting relationship for " + userFromId + " and " + userToId);
            return (Relationship) entityManager.createNativeQuery(get, Relationship.class)
                    .setParameter("userFromId", userFromId)
                    .setParameter("userToId", userToId)
                    .getSingleResult();
        } catch (Exception e){
            log.error("Getting is failed");
            throw new SystemException("500: Something gone wrong.");
        }
    }

    @Transactional
    public List<Relationship> getIncomeRequests(Long userId) throws SystemException {
        try {
            log.info("RelationshipDAO getIncomeRequests method. Getting requests for " + userId);
            return entityManager.createNativeQuery(getIncomeRequests, Relationship.class)
                    .setParameter("userToId", userId)
                    .getResultList();
        } catch (Exception e) {
            log.error("Getting is failed");
            throw new SystemException("500: Something gone wrong.");
        }
    }

    @Transactional
    public List<Relationship> getOutcomeRequests(Long userId) throws SystemException {
        try {
            log.info("RelationshipDAO getOutcomeRequests method. Getting requests for " + userId);
            return entityManager.createNativeQuery(getOutcomeRequests, Relationship.class)
                    .setParameter("userFromId", userId)
                    .getResultList();
        } catch (Exception e) {
            log.error("Getting is failed");
            throw new SystemException("500: Something gone wrong.");
        }
    }

    @Transactional
    public List<Relationship> getFriendsList(Long userId) throws SystemException {
        try {
            log.info("RelationshipDAO getFriendsList method. Getting friends for " + userId);
            return entityManager.createNativeQuery(getFriendsList, Relationship.class)
                    .setParameter("userFromId", userId)
                    .setParameter("userToId", userId)
                    .getResultList();
        } catch (Exception e) {
            log.error("Getting is failed");
            throw new SystemException("500: Something gone wrong.");
        }
    }

    @Override
    public Relationship save(Relationship relationship) throws SystemException {
        return super.save(relationship);
    }

    @Override
    public Relationship update(Relationship relationship) throws SystemException {
        return super.update(relationship);
    }
}
