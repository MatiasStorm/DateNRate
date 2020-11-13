package easyon.dating.app.repository;

import easyon.dating.app.mapper.UserMapper;
import easyon.dating.app.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.List;

@Component
public class UserDAO {

    private final JdbcTemplate jdbcTemplate;
    private final String table = "users";
    private final UserMapper userMapper = new UserMapper();
    private final String selectStatement = "SELECT "
            + "users.user_id, users.first_name, users.created, users.date_of_birth, users.user_description, "
            + "users.last_name, users.profile_picture, users.email, users.username, users.password, users.is_male, "
            + "ROUND(AVG(rating), 1) as rating, COUNT(*) as amount_of_ratings, towns.town_id, towns.town_name, towns.postal_code from user_ratings"
            + " RIGHT JOIN users on user_ratings.target_user_id = users.user_id"
            + " LEFT JOIN towns on users.town_id = towns.town_id ";

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private String createSelect(String where){
        return selectStatement + where + " GROUP by user_id";
    }

    public List<User> selectUsers(){
        return jdbcTemplate.query(
                createSelect(""),
                userMapper
        );
    }

    public User getUser(int id){
        return jdbcTemplate.queryForObject(
                createSelect(" WHERE user_id =  ?"),
               userMapper,
               id
        );
    }

    public List<User> getUsersByIds(List<Integer> userIds){
        String inSql = '(' + String.join(",", Collections.nCopies(userIds.size(), "?")) + ") ";
        return jdbcTemplate.query(
                createSelect(" WHERE user_id IN " + inSql),
                userMapper,
                userIds.toArray()
        );
    }


    public List<User> getUserSearch(String search) {
        List<User> listOfUsers = jdbcTemplate.query(
                createSelect(" WHERE first_name LIKE ? OR last_name LIKE ? OR username LIKE ? "),
                userMapper,
                "%"+search+"%", "%"+search+"%", "%"+search+"%");

       return listOfUsers;

    }

    public User login(String username, String password){
        List<User> users = jdbcTemplate.query(
            createSelect(" WHERE username = ? AND password = ?"),
            userMapper,
            username,
            password
        );
        if(users.size() == 0){
            return null;
        }
        return users.get(0);
    }



    public int createUser(User user){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT into users(first_name, last_name, email, password, username, date_of_birth, is_male, town_id, user_description) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                        new String[]{"user_id"}
                    );
                    preparedStatement.setString(1, user.getFirstName() );
                    preparedStatement.setString(2, user.getLastName());
                    preparedStatement.setString(3, user.getEmail());
                    preparedStatement.setString(4, user.getPassword());
                    preparedStatement.setString(5, user.getUsername());
                    preparedStatement.setDate(6, user.getDateOfBirth());
                    preparedStatement.setBoolean(7, user.getIsMale());
                    preparedStatement.setInt(8, user.getTown().getTownId());
                    preparedStatement.setString(9, user.getUserDescription());
                    return preparedStatement;
                }, keyHolder );

        Number userId = keyHolder.getKey();
        return userId.intValue();
    }

    public User updateUser(User user){
        jdbcTemplate.update(
                "UPDATE users " +
                        "SET first_name = ?, last_name = ?, email = ?, password = ?, username = ?, date_of_birth = ?" +
                        ", is_male = ?, profile_picture = ?, town_id = ?, user_description = ? " +
                        "WHERE user_id = ?",
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getUsername(),
                user.getDateOfBirth(),
                user.getIsMale(),
                user.getProfilePicture(),
                user.getTown().getTownId(),
                user.getUserDescription(),
                user.getUserId()
        );
        return getUser(user.getUserId());
    }

    public void deleteUser(int userId){
        // messages, ratings, tags, favorite
        jdbcTemplate.update("DELETE FROM user_tags WHERE user_id  = ?", userId);
        jdbcTemplate.update("DELETE FROM user_ratings WHERE target_user_id = ? OR creator_user_id = ? ", userId, userId);
        jdbcTemplate.update("DELETE FROM favorites WHERE user_id = ? OR favorite_user_id = ?", userId, userId);
        jdbcTemplate.update("DELETE FROM messages WHERE sender_id = ? OR reciever_id = ?", userId, userId);
        jdbcTemplate.update("DELETE FROM " + table + " WHERE user_id = ?", userId);
    }

    private List<User> getUserByWhere(String whereClause, String param){
        return jdbcTemplate.query(
                createSelect(whereClause),
                userMapper,
                param
        );
    }

    public List<User> getUserByUsername(String username){
        return getUserByWhere("WHERE username = ?", username);
    }

    public List<User> getUserByEmail(String email){
        return getUserByWhere("WHERE email = ?", email);
    }

    public List<User> getTheFiveNewestProfiles(){
        return jdbcTemplate.query("SELECT * FROM " + table + " ORDER BY user_id DESC LIMIT 5", userMapper);
    }

    public List<User> getTopRatedProfiles(){
        return jdbcTemplate.query( createSelect("") + " ORDER BY rating DESC LIMIT 5", userMapper);
    }
}
