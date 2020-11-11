package easyon.dating.app.mapper;

import easyon.dating.app.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;




public class UserMapper implements RowMapper<User>{


    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        try {
            TownMapper townMapper = new TownMapper();
            user.setTown(townMapper.mapRow(resultSet, i));
        }
        catch(SQLException ex){
            user.setTown(null);
        }
        user.setUserId(resultSet.getInt("user_id"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setEmail(resultSet.getString("email"));
        user.setTownId(resultSet.getInt("town_id"));
        String password = resultSet.getString("password");
        user.setPassword(password);
        user.setPassword2(password);
        user.setUsername(resultSet.getString("username"));
        String profilePicture = resultSet.getString("profile_picture");
        if(profilePicture != null){
            if(profilePicture.length() == 0){
                profilePicture = "/images/mand_default.webp";
            }
        }
        user.setProfilePicture(profilePicture);
        user.setDateOfBirth(resultSet.getDate("date_of_birth"));
        user.setCreated(resultSet.getDate("created"));
        user.setUserDescription(resultSet.getString("user_description"));
        try {
            user.setAvgRating(resultSet.getDouble("rating"));
        }
        catch  (SQLException e){
            user.setAvgRating(0);
        }

        return user;
    }





}
