package com.findme.service;

import com.findme.dao.MessageDAO;
import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.NotFoundException;
import com.findme.exceptions.SystemException;
import com.findme.models.Message;
import com.findme.models.RelationshipStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;
    private RelationshipService relationshipService;
    private static final Logger log = Logger.getLogger(MessageService.class);

    @Autowired
    public MessageService(MessageDAO messageDAO, RelationshipService relationshipService) {
        this.messageDAO = messageDAO;
        this.relationshipService = relationshipService;
    }

    public List<Message> get(Long firstUserId, Long secondUserId) {
        return messageDAO.get(firstUserId, secondUserId);
    }

    public Message readMessage(Message message) throws SystemException, NotFoundException, BadRequestException{
        log.info("MessageService readMessage method.");
        if (message.getDateRead() != null || messageDAO.get(message.getId()) == null){
            log.error("Wrong read logic");
            throw new BadRequestException("Message must exist and be not read.");
        }

        message.setDateRead(new Date());
        return messageDAO.update(message);
    }

    public Message writeMessage(Message message) throws SystemException, BadRequestException {
        log.info("MessageService writeMessage method.");
        validateWriteMessage(message);

        message.setDateSent(new Date());
        return messageDAO.save(message);
    }

    public Message update(Message message) throws SystemException, BadRequestException {
        validateUpdate(message);
        message.setDateEdited(new Date());
        return messageDAO.update(message);
    }

    public void delete(Long id) throws SystemException, NotFoundException, BadRequestException {
        Message message = messageDAO.get(id);
        if (message.getDateDeleted() != null || message.getDateRead()!=null){
            log.error("Message was already read or deleted.");
            throw new BadRequestException("Message was already read or deleted.");
        }
        message.setDateDeleted(new Date());
        messageDAO.update(message);
    }

    private void validateWriteMessage(Message message) throws BadRequestException{
        if (!relationshipService.get(message.getUserFrom().getId(), message.getUserTo().getId()).getStatus().equals(RelationshipStatus.FRIENDS)) {
            log.error("Bad request. Users must be friends");
            throw new BadRequestException("You must be friends.");
        }
        if (message.getText().length()>140){
            log.error("Bad request. Text must be not longer then 140 symbols");
            throw new BadRequestException("Text must be not longer then 140 symbols");
        }
    }

    private void validateUpdate(Message message) throws BadRequestException{
        validateWriteMessage(message);
        if (message.getDateRead()!=null || message.getDateEdited()!=null || message.getDateDeleted()!=null){
            log.error("Bad request. Message is already read or edited or deleted.");
            throw new BadRequestException("Message is already read or edited or deleted.");
        }
    }
}
