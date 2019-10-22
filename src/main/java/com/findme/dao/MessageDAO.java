package com.findme.dao;

import com.findme.exceptions.NotFoundException;
import com.findme.exceptions.SystemException;
import com.findme.models.Message;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class MessageDAO extends GeneralDAO<Message> {
    private static final String get = "SELECT * FROM MESSAGES WHERE (USER_FROM_ID = :userFromId AND USER_TO_ID = :userToId)" +
            " OR (USER_FROM_ID = :userFromId AND USER_TO_ID = :userToId ) AND DATE_DELETED IS NOT NULL;";

    private static final Logger log = Logger.getLogger(MessageDAO.class);

    public MessageDAO() {
        settClass(Message.class);
    }

    @PersistenceContext
    private EntityManager entityManager;

    public List<Message> get(Long firstUserId, Long secondUserId) {
        log.info("MessageDAO get method. Getting list of messages by two ids.");
        return entityManager.createNativeQuery(get)
                .setParameter("userFromId", firstUserId)
                .setParameter("userToId", secondUserId)
                .getResultList();
    }

    @Override
    public Message get(Long id) throws NotFoundException, SystemException {
        return super.get(id);
    }

    @Override
    public Message save(Message message) throws SystemException {
        log.info("MessageDAO save method. Saving message.");
        return super.save(message);
    }

    @Override
    public Message update(Message message) throws SystemException {
        log.info("MessageDAO update method. Updating message.");
        return super.update(message);
    }

    @Override
    public void delete(Long id) throws SystemException {
        log.info("MessageDAO delete method. Deleting message.");
        super.delete(id);
    }
}
