package easyon.dating.app.mapper;

import easyon.dating.app.models.Favorite;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FavoriteMapper implements RowMapper<Favorite> {


    @Override
    public Favorite mapRow(ResultSet resultSet, int i) throws SQLException {
        Favorite favorite = new Favorite();
        favorite.setFavoriteUserId(resultSet.getInt("favorite_user_id"));
        favorite.setUserId(resultSet.getInt("user_id"));
        return favorite;
    }
}
