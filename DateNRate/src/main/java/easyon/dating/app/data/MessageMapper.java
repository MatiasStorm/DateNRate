package easyon.dating.app.data;

import easyon.dating.app.models.Message;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageMapper implements RowMapper<Message> {
    @Override
    public Message mapRow(ResultSet resultSet, int i) throws SQLException {
        Message message = new Message();
        message.setMessageId(resultSet.getInt("message_id"));
        message.setSenderId(resultSet.getInt("sender_id"));
        message.setRecieverId(resultSet.getInt("reciever_id"));
        message.setMessageDate(resultSet.getTimestamp("message_date"));
        message.setMessage(resultSet.getString("message"));
        message.setRead(resultSet.getBoolean("is_read"));
        return message;
    }
}
