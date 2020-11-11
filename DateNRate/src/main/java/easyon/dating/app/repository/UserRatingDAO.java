package easyon.dating.app.repository;

import easyon.dating.app.mapper.UserRatingMapper;
import easyon.dating.app.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserRatingDAO {

    private final String table = "user_ratings";
    private final JdbcTemplate jdbcTemplate;
    private final String selectStatement = "SELECT "
            + "user_ratings.target_user_id, user_ratings.rating_id, ratings.rating_name, AVG(user_ratings.rating) as rating FROM user_ratings "
            + "JOIN ratings on user_ratings.rating_id = ratings.rating_id ";
    private final UserRatingMapper userRatingMapper = new UserRatingMapper();


    @Autowired
    public UserRatingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private String createSelect(String where){
        return selectStatement + where + " GROUP by rating_id, target_user_id ";
    }

    public List<UserRating> getUserRatingList(int userId) {
        return jdbcTemplate.query(createSelect(" WHERE target_user_id = ?")
                , userRatingMapper
                ,userId);
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
