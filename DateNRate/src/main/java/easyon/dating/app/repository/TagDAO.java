package easyon.dating.app.repository;

import easyon.dating.app.mapper.TagMapper;
import easyon.dating.app.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class TagDAO {

    private final JdbcTemplate jdbcTemplate;
    private final String table = "tags";

    @Autowired
    public TagDAO(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate;}

    public List<Tag> getTagList() {
        return jdbcTemplate.query(
                "SELECT * FROM " + table,
                new TagMapper()
                );
    }

}
