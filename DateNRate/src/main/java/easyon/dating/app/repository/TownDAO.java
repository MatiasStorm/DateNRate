package easyon.dating.app.repository;

import easyon.dating.app.mapper.TownMapper;
import easyon.dating.app.models.Town;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public class TownDAO {

    private final JdbcTemplate jdbcTemplate;
    private final String table = "towns";

    @Autowired
    public TownDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Town getTownByPostcalCode(int postalCode) throws SQLException{
        return jdbcTemplate.queryForObject(
                "SELECT * FROM " + table + " WHERE postal_code = ?",
                new TownMapper(),
                postalCode
        );
    }
}
