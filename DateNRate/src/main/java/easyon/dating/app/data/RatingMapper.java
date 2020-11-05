package easyon.dating.app.data;

import easyon.dating.app.models.Rating;
import org.springframework.jdbc.core.RowMapper;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RatingMapper implements RowMapper<Rating> {


    @Override
    public Rating mapRow(ResultSet resultSet, int i) throws SQLException {
        Rating rating = new Rating();
        rating.setRatingId(resultSet.getInt("rating_id"));
        rating.setRatingName(resultSet.getString("rating_name"));
        return rating;
    }
}
