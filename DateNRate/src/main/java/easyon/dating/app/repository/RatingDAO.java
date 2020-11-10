package easyon.dating.app.repository;

import easyon.dating.app.mapper.RatingMapper;
import easyon.dating.app.models.Rating;
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

    public void addToUserRating(Rating rating){
        jdbcTemplate.update(
                "INSERT into user_ratings(target_user_id, rating, rating_id, creator_user_id) VALUES (?,?,?,?) ",
                rating.getRatingId(),
                rating.getRatingName()
        );
    }

}

