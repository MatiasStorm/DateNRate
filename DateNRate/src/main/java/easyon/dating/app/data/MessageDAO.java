package easyon.dating.app.data;

import easyon.dating.app.models.Message;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageDAO {
    private final JdbcTemplate jdbcTemplate;

    public MessageDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createMessage(Message message){
        jdbcTemplate.update(
                "INSERT INTO messages (sender_id, reciever_id, message) VALUES(?, ?, ?)",
                message.getSenderId(),
                message.getRecieverId(),
                message.getMessage()
        );
    }
}
