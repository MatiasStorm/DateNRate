package easyon.dating.app.mapper;

import easyon.dating.app.models.UserTag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserTagMapper implements RowMapper<UserTag> {

    @Override
    public UserTag mapRow(ResultSet resulSet, int i) throws SQLException {
        UserTag userTag = new UserTag();
        userTag.setTagId(resulSet.getInt("tag_id"));
        userTag.setUserId(resulSet.getInt("tag_name"));

        return userTag;
    }

}

