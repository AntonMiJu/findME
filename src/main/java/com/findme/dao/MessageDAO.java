package com.findme.dao;

import com.findme.exceptions.SystemException;
import com.findme.models.Message;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Log4j
@Repository
@Transactional
public class MessageDAO extends GeneralDAO<Message> {
    private static final String getByTwoIds = "SELECT * FROM MESSAGES WHERE (USER_FROM_ID = :userFromId AND USER_TO_ID = :userToId)" +
            " OR (USER_FROM_ID = :userFromId AND USER_TO_ID = :userToId ) AND DATE_DELETED IS NOT NULL;";

    public MessageDAO() {
        settClass(Message.class);
    }

    @PersistenceContext
    private EntityManager entityManager;

    public List<Message> getByTwoIds(Long firstUserId, Long secondUserId) {
        log.info("MessageDAO get method. Getting list of messages by two ids.");
        return entityManager.createNativeQuery(getByTwoIds)
                .setParameter("userFromId", firstUserId)
                .setParameter("userToId", secondUserId)
                .getResultList();
    }

    @Override
    public Message get(Long id) throws SystemException {
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
