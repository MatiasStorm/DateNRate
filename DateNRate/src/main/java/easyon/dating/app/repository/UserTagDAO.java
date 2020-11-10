package easyon.dating.app.repository;

import easyon.dating.app.mapper.UserTagMapper;
import easyon.dating.app.models.UserTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

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


}


