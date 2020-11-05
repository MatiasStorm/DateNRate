package easyon.dating.app.data;

import easyon.dating.app.models.Favorite;
import easyon.dating.app.models.Town;
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
