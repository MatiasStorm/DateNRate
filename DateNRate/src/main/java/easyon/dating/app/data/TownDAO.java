package easyon.dating.app.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TownDAO {

    private final JdbcTemplate jdbcTemplate;
    private final String table = "towns";

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getTownName(int townId){
        return jdbcTemplate.query("")
    }
}
