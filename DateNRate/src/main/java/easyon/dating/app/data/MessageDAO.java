package easyon.dating.app.data;

import easyon.dating.app.models.Message;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;

@Component
public class MessageDAO {
    private final JdbcTemplate jdbcTemplate;
    private final String table = "messages";
    private final MessageMapper messageMapper = new MessageMapper();

    public MessageDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createMessage(Message message){
        jdbcTemplate.update(
                "INSERT INTO " + table + "(sender_id, reciever_id, message) VALUES(?, ?, ?)",
                message.getSenderId(),
                message.getRecieverId(),
                message.getMessage()
        );
    }

    public List<Integer> getSenderUserIds(int userId){
        return jdbcTemplate.query(
                "SELECT DISTINCT sender_id FROM " + table + " WHERE reciever_id = ?",
                (ResultSet rs, int rowCount) -> {
                    return rs.getInt("sender_id");
                },
                userId
        );
    }

    public List<Message> getUserMessages(int recieverId, int senderId){
        return jdbcTemplate.query(
                "SELECT * FROM " + table
                        + " WHERE "
                        + "(reciever_id = ? AND sender_id = ?)"
                        + "OR (sender_id = ? AND reciever_id = ?)"
                        + "ORDER BY message_date ASC",
                messageMapper,
                recieverId,
                senderId,
                recieverId,
                senderId
        );
    }
}
