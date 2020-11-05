package easyon.dating.app.models;

import java.time.LocalDateTime;
import java.util.Date;

public class Message {

    private int messageId;
    private int senderId;
    private int recieverId;
    private Date messageDate;
    private String message;
    private boolean isRead;
    private User senderUser;
    private User recieverUser;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getRecieverId() {
        return recieverId;
    }

    public void setRecieverId(int recieverId) {
        this.recieverId = recieverId;
    }

    public Date getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(Date massageDate) {
        this.messageDate = massageDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public User getSenderUser() {
        return senderUser;
    }

    public void setSenderUser(User senderUser) {
        this.senderUser = senderUser;
    }

    public User getRecieverUser() {
        return recieverUser;
    }

    public void setRecieverUser(User recieverUser) {
        this.recieverUser = recieverUser;
    }
}
