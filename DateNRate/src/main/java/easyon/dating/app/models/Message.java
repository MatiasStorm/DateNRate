package easyon.dating.app.models;

import java.time.LocalDateTime;

public class Message {

    private int messageId;
    private int senderId;
    private int recieverId;
    private LocalDateTime massageDate;
    private String massage;
    private boolean isRead;

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

    public LocalDateTime getMassageDate() {
        return massageDate;
    }

    public void setMassageDate(LocalDateTime massageDate) {
        this.massageDate = massageDate;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
