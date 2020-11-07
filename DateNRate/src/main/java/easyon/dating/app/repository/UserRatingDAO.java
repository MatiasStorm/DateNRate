package easyon.dating.app.repository;

import easyon.dating.app.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class UserRatingDAO {

    private final String table = "user_ratings";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRatingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createUserRating(UserRating userRating) {
        jdbcTemplate.update(
                "INSERT INTO " + table + " (target_user_id, rating, rating_id, creator_user_id) VALUES (?,?,?,?) ",
                userRating.getTargetUserId(),
                userRating.getRating(),
                userRating.getRatingId(),
                userRating.getCreatorUserId()
        );
    }
}
