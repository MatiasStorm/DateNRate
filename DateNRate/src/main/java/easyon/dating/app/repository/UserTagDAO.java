package easyon.dating.app.repository;

import easyon.dating.app.mapper.UserTagMapper;
import easyon.dating.app.models.Tag;
import easyon.dating.app.models.User;
import easyon.dating.app.models.UserTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class UserTagDAO {

    private final JdbcTemplate jdbcTemplate;
    private final String table = "user_tags";

    @Autowired
    public UserTagDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addTagToUser(UserTag userTag){
        jdbcTemplate.update(
                "INSERT INTO " + table + "(tag_id, user_id) VALUES(?, ?)",
                userTag.getTagId(),
                userTag.getUserId()
        );
    }
    public void removeTagFromUser(UserTag userTag){
        jdbcTemplate.update(
                "DELETE FROM " + table + " WHERE tag_id = ? AND user_id = ?",
                userTag.getTagId(),
                userTag.getUserId()
        );
    }

    public List<UserTag> getActiveTagList(int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM " + table + " WHERE user_id = ?",
                new UserTagMapper(),
                userId

        );
    }
}


