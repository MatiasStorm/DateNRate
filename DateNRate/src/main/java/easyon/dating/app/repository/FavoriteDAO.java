package easyon.dating.app.repository;

import easyon.dating.app.mapper.FavoriteMapper;
import easyon.dating.app.models.Favorite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FavoriteDAO {

    private final JdbcTemplate jdbcTemplate;
    private final String table = "favorites";
    private final FavoriteMapper favoriteMapper = new FavoriteMapper();

    @Autowired
    public FavoriteDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Favorite> getFavoriteList(int id) {

        return jdbcTemplate.query(
                "SELECT * FROM " + table + " WHERE user_id = ?" ,
                favoriteMapper,
                id
        );
    }

    public void addToFavorite(Favorite favorite){
        jdbcTemplate.update(
                "INSERT into favorites(user_id, favorite_user_id) VALUES(?, ?)",
                favorite.getUserId(),
                favorite.getFavoriteUserId()
        );

    }
}
