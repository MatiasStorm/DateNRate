package easyon.dating.app.services;

import easyon.dating.app.data.MessageDAO;
import easyon.dating.app.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final MessageDAO messageDao;

    @Autowired
    public MessageService(MessageDAO messageDao) {
        this.messageDao = messageDao;
    }

    public void createMessage(Message message){
        messageDao.createMessage(message);
    }
}
