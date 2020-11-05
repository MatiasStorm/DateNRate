package easyon.dating.app.data;

import easyon.dating.app.models.Message;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageMapper implements RowMapper<Message> {
    @Override
    public Message mapRow(ResultSet resultSet, int i) throws SQLException {
        return null;
    }
}
