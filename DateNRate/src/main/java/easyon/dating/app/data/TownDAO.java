package easyon.dating.app.data;

import easyon.dating.app.models.Rating;
import easyon.dating.app.models.Town;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TownDAO {

    private final JdbcTemplate jdbcTemplate;
    private final String table = "towns";

    @Autowired
    public TownDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Town> getTownList() {
        return jdbcTemplate.query(
                "SELECT * FROM " + table,
                new TownMapper()
        );
    }

    public Town getTown(int townId) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM " + table + " WHERE town_id = ?",
                new TownMapper(),
                townId
        );
    }
}
