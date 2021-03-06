package easyon.dating.app.services;

import easyon.dating.app.repository.MessageDAO;
import easyon.dating.app.repository.UserDAO;
import easyon.dating.app.models.Message;
import easyon.dating.app.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<User> getConversationUsers(int recieverId){
        List<Integer> conversationUserIds = messageDao.getConversationUserIds(recieverId);
        if(conversationUserIds.size() == 0){
            return new ArrayList<>();
        }
        return userDAO.getUsersByIds(conversationUserIds);
    }

    public List<Message> getConversation(int recieverId, int senderId){
        List<Message> messages = messageDao.getUserMessages(recieverId, senderId);
        return messages;
    }
}
