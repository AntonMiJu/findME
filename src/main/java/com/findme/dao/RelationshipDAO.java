package com.findme.dao;

import com.findme.exceptions.NotFoundException;
import com.findme.exceptions.SystemException;
import com.findme.models.Relationship;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RelationshipDAO extends GeneralDAO<Relationship> {
    private static final String get = "SELECT * FROM RELATIONSHIPS WHERE USER_FROM_ID = :userFromId " +
            "AND USER_TO_ID = :userToId;";
    private static final String getIncomeRequests = "SELECT USER_FROM_ID FROM RELATIONSHIPS WHERE USER_TO_ID = :userToId" +
            "AND STATUS = 'REQUEST_SENT'";
    private static final String getOutcomeRequests = "SELECT USER_TO_ID FROM RELATIONSHIPS WHERE USER_FROM_ID = :userFromId" +
            "AND STATUS = 'REQUEST_SENT'";
    private static final String getFriendsList = "SELECT * FROM RELATIONSHIPS WHERE (USER_FROM_ID = :userFromId" +
            " OR USER_TO_ID = :userToId) AND STATUS = 'FRIENDS'";

    @PersistenceContext
    private EntityManager entityManager;

    public Relationship get(Long userFromId, Long userToId) {
        return (Relationship) entityManager.createNativeQuery(get, Relationship.class)
                .setParameter("userFromId", userFromId)
                .setParameter("userToId", userToId)
                .getSingleResult();
    }

    public List<Relationship> getIncomeRequests(Long userId) throws SystemException{
        try {
            return entityManager.createNativeQuery(getIncomeRequests, Relationship.class)
                    .setParameter("userToId", userId)
                    .getResultList();
        } catch (Exception e){
            throw new SystemException("500: Something gone wrong.");
        }
    }

    public List<Relationship> getOutcomeRequests(Long userId) throws SystemException{
        try {
            return entityManager.createNativeQuery(getOutcomeRequests, Relationship.class)
                    .setParameter("userFromId", userId)
                    .getResultList();
        } catch (Exception e){
            throw new SystemException("500: Something gone wrong.");
        }
    }

    public List<Relationship> getFriendsList(Long userId) throws SystemException{
        try {
            return entityManager.createNativeQuery(getFriendsList, Relationship.class)
                    .setParameter("userFromId", userId)
                    .setParameter("userToId",userId)
                    .getResultList();
        } catch (Exception e){
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
