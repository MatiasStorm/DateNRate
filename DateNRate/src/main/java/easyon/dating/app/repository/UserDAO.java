package easyon.dating.app.repository;

import easyon.dating.app.mapper.UserMapper;
import easyon.dating.app.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

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
            + "AVG(rating) as rating, towns.town_id, towns.town_name, towns.postal_code from user_ratings"
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
//                "SELECT * FROM " + table + " LEFT JOIN towns on users.town_id = towns.town_id",
                userMapper
        );
    }

    public User getUser(int id){
        return jdbcTemplate.queryForObject(
                createSelect(" WHERE user_id =  ?"),
//                "SELECT * FROM " + table
//                        + " LEFT JOIN towns on users.town_id = towns.town_id"
//                        + " WHERE user_id =  ? ",
               userMapper,
               id
        );
    }

    public List<User> getUsersByIds(List<Integer> userIds){
        String inSql = '(' + String.join(",", Collections.nCopies(userIds.size(), "?")) + ") ";
        return jdbcTemplate.query(
                createSelect(" WHERE user_id IN " + inSql),
//                "SELECT * FROM " + table
//                        + " LEFT JOIN towns on users.town_id = towns.town_id"
//                        + " WHERE user_id IN " + inSql,
                userMapper,
                userIds.toArray()
        );
    }


    public List<User> getUserSearch(String search) {
        List<User> listOfUsers = jdbcTemplate.query(
                createSelect(" WHERE first_name LIKE ? OR last_name LIKE ? OR username LIKE ? "),
//                "SELECT * FROM users"
//                        + " LEFT JOIN towns on users.town_id = towns.town_id"
//                        + " WHERE first_name LIKE ? OR last_name LIKE ? OR username like?"
                userMapper,
                "%"+search+"%", "%"+search+"%", "%"+search+"%");

       return listOfUsers;

    }

    public User login(String username, String password){
        return jdbcTemplate.queryForObject(
                createSelect(" WHERE username = ? AND password = ?"),
//                "SELECT * from users" + " LEFT JOIN towns on users.town_id = towns.town_id"
                userMapper,
                username,
                password
        );
    }



    public User createUser(User user){
        jdbcTemplate.update(
                "INSERT into users(first_name, last_name, email, password, username, date_of_birth, is_male, town_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getUsername(),
                user.getDateOfBirth(),
                user.getIsMale(),
                user.getTown().getTownId()
        );
        int newUserId = jdbcTemplate.queryForObject("SELECT last_insert_id() as id", (rs, i) -> rs.getInt("id"));
        return getUser(newUserId);
    }

    public User updateUser(User user){
        jdbcTemplate.update(
                "UPDATE users " +
                        "SET first_name = ?, last_name = ?, email = ?, password = ?, username = ?, date_of_birth = ?, is_male = ?, profile_picture = ?, town_id = ? " +
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
                user.getUserId()
        );
        return getUser(user.getUserId());
    }

    private List<User> getUserByWhere(String whereClause, String param){
        return jdbcTemplate.query(
                createSelect(whereClause),
//                "SELECT * FROM " + table + " LEFT JOIN towns on users.town_id = towns.town_id "
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
        return jdbcTemplate.query("SELECT * FROM " + table + " ORDER BY user_id LIMIT 5", userMapper);
    }
}
