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


    public List<UserTag> getUserTagList() {
        return jdbcTemplate.query(
                "SELECT * FROM " + table,
                new UserTagMapper()
        );
    }

    public UserTag getUserTag(int userTag) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM " + table + " WHERE tag_id = ?",
                new UserTagMapper(),
                userTag

        );
    }

    public void addTagToUser(UserTag userTag, int userId){
        jdbcTemplate.update(
                "INSERT INTO " + table + "(tag_id, user_id) VALUES(?, ?)",
                userTag.getTagId(),
                userId
        );
    }
    public void removeTagFromUser(UserTag userTag, int userId){
        jdbcTemplate.update(
                "DELETE FROM " + table + " WHERE tag_id = ? AND user_id = ?",
                userTag.getTagId(),
                userId
        );
    }

    public UserTag getUsersTag(int tagId, int userId) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM " + table + " WHERE tag_id = ? AND user_id = ?",
                new UserTagMapper(),
                tagId,
                userId

        );
    }

    public List<UserTag> getActiveTagList(int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM " + table + " WHERE user_id = ?",
                new UserTagMapper(),
                userId

        );
    }

    public List<UserTag> getInactiveTagList(int userId, int tagId) {
        return jdbcTemplate.query(
                "SELECT * FROM " + table + " WHERE user_id != ?",
                new UserTagMapper(),
                userId

        );
    }





}


