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
//        ---------------HVEM FANDEN LAVEDE DEN HER ?????? JØRGEN??!!!!!!!! ------------------------------
//        userTag.setUserId(resulSet.getInt("tag_name"));
        userTag.setUserId(resulSet.getInt("user_id"));

        return userTag;
    }

}

