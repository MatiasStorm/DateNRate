package easyon.dating.app.services;

import easyon.dating.app.data.MessageDAO;
import easyon.dating.app.models.Message;

public class MessageService {
    private final MessageDAO messageDao;

    public MessageService(MessageDAO messageDao) {
        this.messageDao = messageDao;
    }

    public void createMessage(Message message){
        messageDao.createMessage(message);
    }
}
