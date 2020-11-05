package easyon.dating.app.data;

import easyon.dating.app.models.Rating;
import easyon.dating.app.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RatingDAO {
    private final JdbcTemplate jdbcTemplate;
    private final String table = "ratings";


    @Autowired
    public RatingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Rating> selectRatings(){
        return jdbcTemplate.query(
                "SELECT * FROM " + table,
                new RatingMapper()
        );
    }

}

