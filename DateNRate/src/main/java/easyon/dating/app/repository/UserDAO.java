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

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> selectUsers(){
        return jdbcTemplate.query(
                "SELECT * FROM " + table + " LEFT JOIN towns on users.town_id = towns.town_id",
                userMapper
        );
    }

    public User getUser(int id){
        return jdbcTemplate.queryForObject(
                "SELECT * FROM " + table
                        + " LEFT JOIN towns on users.town_id = towns.town_id"
                        + " WHERE user_id =  ? ",
               userMapper,
               id
        );
    }

    public List<User> getUsersByIds(List<Integer> userIds){
        String inSql = '(' + String.join(",", Collections.nCopies(userIds.size(), "?")) + ") ";
        return jdbcTemplate.query(
                "SELECT * FROM " + table
                        + " LEFT JOIN towns on users.town_id = towns.town_id"
                        + " WHERE user_id IN " + inSql,
                userMapper,
                userIds.toArray()
        );
    }


    public List<User> getUserSearch(String search) {
        List<User> listOfUsers = jdbcTemplate.query(
                "SELECT * FROM users"
                        + " LEFT JOIN towns on users.town_id = towns.town_id"
                        + " WHERE first_name LIKE ? OR last_name LIKE ? OR username like?"
                ,userMapper, "%"+search+"%", "%"+search+"%", "%"+search+"%");

       return listOfUsers;

    }

    public User login(String username, String password){
        return jdbcTemplate.queryForObject(
                "SELECT * from users" + " LEFT JOIN towns on users.town_id = towns.town_id" + " WHERE username = ? AND password = ?",
                userMapper,
                username,
                password
        );
    }



    public User createUser(User user){
        jdbcTemplate.update(
                "INSERT into users(first_name, last_name, email, password, username, date_of_birth, is_male) VALUES(?, ?, ?, ?, ?, ?, ?)",
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getUsername(),
                user.getDateOfBirth(),
                user.getIsMale()
        );
        int newUserId = jdbcTemplate.queryForObject("SELECT last_insert_id() as id", (rs, i) -> rs.getInt("id"));
        return getUser(newUserId);
    }

    public User updateUser(User user){
        jdbcTemplate.update(
                "UPDATE users " +
                        "SET first_name = ?, last_name = ?, email = ?, password = ?, username = ?, date_of_birth = ?, is_male = ?, profile_picture = ? " +
                        "WHERE user_id = ?",
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getUsername(),
                user.getDateOfBirth(),
                user.getIsMale(),
                user.getProfilePicture(),
                user.getUserId()
        );
        return getUser(user.getUserId());
    }

    private List<User> getUserByWhere(String whereClause, String param){
        return jdbcTemplate.query(
                "SELECT * FROM " + table + " LEFT JOIN towns on users.town_id = towns.town_id " + whereClause,
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
<<<<<<< HEAD
        return jdbcTemplate.query("SELECT * FROM " + table + " ORDER BY user_id LIMIT 5", userMapper);
=======
        return jdbcTemplate.query(
                "SELECT * FROM " + table + " LEFT JOIN towns on users.town_id = towns.town_id "
                + " ORDER BY user_id DESC LIMIT 5"
                , userMapper);
>>>>>>> 55b66a65f3d67334b5d76ae33f8ea17e561c37b8
    }
}
