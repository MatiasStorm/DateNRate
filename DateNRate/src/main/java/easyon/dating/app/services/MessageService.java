package easyon.dating.app.services;

import easyon.dating.app.repository.MessageDAO;
import easyon.dating.app.repository.UserDAO;
import easyon.dating.app.models.Message;
import easyon.dating.app.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageDAO messageDao;
    private final UserDAO userDAO;

    @Autowired
    public MessageService(MessageDAO messageDao, UserDAO userDAO) {
        this.messageDao = messageDao;
        this.userDAO = userDAO;
    }

    public void createMessage(Message message){
        messageDao.createMessage(message);
    }

    public List<User> getSenders(int recieverId){
        return getSenders(recieverId, 0);
    }

    public List<User> getSenders(int recieverId, int activeUserId){
        List<Integer> senderUserIds = messageDao.getSenderUserIds(recieverId);
        senderUserIds.add(activeUserId);
        List<User> senders = userDAO.getUsersByIds(senderUserIds);
        return senders;
    }

    public List<Message> getConversation(int recieverId, int senderId){
        List<Message> messages = messageDao.getUserMessages(recieverId, senderId);
        return messages;
    }
}
