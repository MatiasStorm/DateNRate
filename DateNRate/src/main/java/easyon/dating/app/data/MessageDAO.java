package easyon.dating.app.data;

import easyon.dating.app.models.Message;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageDAO {
    private final JdbcTemplate jdbcTemplate;
    private final String table = "messages";

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

    public List<Message> getUserMessages(int userId){
        return jdbcTemplate.query(
                "SELECT * FROM " + table + " WHERE table_id = ?",
                new MessageMapper(),
                userId
        );
    }
}
