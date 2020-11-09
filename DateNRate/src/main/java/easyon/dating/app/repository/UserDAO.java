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
                "SELECT * FROM " + table,
                userMapper
        );
    }

    public User getUser(int id){
        return jdbcTemplate.queryForObject(
                "SELECT * FROM " + table + " WHERE user_id =  ?",
               userMapper,
               id
        );
    }

    public List<User> getUsersByIds(List<Integer> userIds){
        String inSql = '(' + String.join(",", Collections.nCopies(userIds.size(), "?")) + ')';
        return jdbcTemplate.query(
                "SELECT * FROM " + table + " WHERE user_id IN " + inSql,
                userMapper,
                userIds.toArray()
        );
    }


    public List<User> getUserSearch(String search) {
        List<User> listOfUsers = jdbcTemplate.query(
                "SELECT * FROM users WHERE first_name LIKE ? OR last_name LIKE ? OR username like ?"
                ,userMapper, search, search, search);

       return listOfUsers;

    }



    public void createUser(User user){
        jdbcTemplate.update(
                "INSERT into users(first_name, last_name, email, password, username, date_of_birth) VALUES(?, ?, ?, ?, ?, ?)",
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getUsername(),
                user.getDateOfBirth()
        );
    }



}
