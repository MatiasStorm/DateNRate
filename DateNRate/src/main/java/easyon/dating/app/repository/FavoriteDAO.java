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

    @Autowired
    public FavoriteDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Favorite> getFavoriteList() {

        return jdbcTemplate.query(
                "SELECT * FROM " + table,
                new FavoriteMapper()
        );
    }
}
