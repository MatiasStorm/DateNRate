package easyon.dating.app.data;

import easyon.dating.app.models.Favorite;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FavoriteMapper implements RowMapper {


    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        Favorite favorite = new Favorite();
        favorite.setFavoriteUserId(resultSet.getInt("user_id"));
        favorite.setUserId(resultSet.getInt("favorite_user_id"));
        return favorite;
    }
}
