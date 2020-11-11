package easyon.dating.app.mapper;

import easyon.dating.app.models.UserRating;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRatingMapper implements RowMapper<UserRating> {

    @Override
    public UserRating mapRow(ResultSet resultSet, int i) throws SQLException {
        UserRating userRating = new UserRating();
        userRating.setTargetUserId(resultSet.getInt("target_user_id"));
        userRating.setUserRatingId(resultSet.getInt("rating_id"));
        userRating.setRatingName(resultSet.getString("ratings.rating_name"));
        userRating.setAvgRating(resultSet.getDouble("rating"));

        return userRating;
    }
}
