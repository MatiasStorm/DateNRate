package easyon.dating.app.repository;

import easyon.dating.app.models.Town;
import easyon.dating.app.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.Date;
import java.util.List;

@JdbcTest
@Sql(value = {"classpath:database.sql"})
class UserDAOTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private UserDAO userDAO;


    private User user1 = new User();
    private User user2 = new User();

    @BeforeEach
    public void setup(){
//        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
//	      .addScript("database.sql")
//	      .build();
//        jdbcTemplate = new JdbcTemplate(dataSource);
        userDAO = new UserDAO(jdbcTemplate);
        user1.setUserId(1);
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setEmail("john@gmail.com");
        user1.setTownId(1);
        user1.setPassword("password");
        user1.setUsername("John22");
        user1.setDateOfBirth(new Date(System.currentTimeMillis()));
        user1.setMale(true);
        Town town1 = new Town();
        town1.setTownId(1);
        user1.setTown(town1);

        user2.setUserId(2);
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setEmail("jane@gmail.com");
        user2.setTownId(2);
        user2.setPassword("password");
        user2.setUsername("Jane22");
        user2.setDateOfBirth(new Date(System.currentTimeMillis()));
        user2.setMale(false);
        Town town2 = new Town();
        town2.setTownId(2);
        user2.setTown(town2);
    }

    @Test
    void createUser(){
        int userId = userDAO.createUser(user1);
        Assertions.assertEquals(userId, 1);
    }

    @Test
    void getUserById(){
        int userId = userDAO.createUser(user1);
        User user = userDAO.getUser(userId);
        Assertions.assertEquals(user.getUserId(), user1.getUserId());
        Assertions.assertEquals(user.getUsername(), user1.getUsername());
        Assertions.assertEquals(user.getEmail(), user1.getEmail());
        Assertions.assertEquals(user.getTown().getPostalCode(), user1.getTown().getPostalCode());
        Assertions.assertEquals(user.getPassword(), user1.getPassword());
    }

//    @Test
//    void selectUsers() {
//    }
//
//    @Test
//    void getUser() {
//    }
}