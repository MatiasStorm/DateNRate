package easyon.dating.app.data;

import easyon.dating.app.models.Town;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TownMapper implements RowMapper<Town> {

    @Override
    public Town mapRow(ResultSet resultSet, int i) throws SQLException {
        Town town = new Town();
        town.setPostalCode(resultSet.getInt("postal_code"));
        town.setTownName(resultSet.getString("town_name"));
        return town;
    }
}
