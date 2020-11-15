package easyon.dating.app.repository;

import easyon.dating.app.models.Town;
import easyon.dating.app.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@JdbcTest
@Transactional
@Sql(value = {"classpath:database.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UserDAOTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private UserDAO userDAO;


    private User user1 = new User();
    private User user2 = new User();

    @BeforeEach
    public void setup(){
        userDAO = new UserDAO(jdbcTemplate);
        user1.setUserId(1);
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setEmail("john@gmail.com");
        user1.setTownId(1);
        user1.setPassword("passwordJOHN");
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
        user2.setPassword("passwordJANE");
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

    private void userAssertions(User expectedUser, User actualUser){
        Assertions.assertEquals(expectedUser.getUserId(), actualUser.getUserId());
        Assertions.assertEquals(expectedUser.getUsername(), actualUser.getUsername());
        Assertions.assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        Assertions.assertEquals(expectedUser.getTown().getTownId(), actualUser.getTown().getTownId());
        Assertions.assertEquals(expectedUser.getPassword(), actualUser.getPassword());
        Assertions.assertEquals(expectedUser.getPassword(), actualUser.getPassword2());
    }

    @Test
    void getUserById(){
        int userId = userDAO.createUser(user1);
        User user = userDAO.getUser(userId);
        userAssertions(user1, user);
    }

    @Test
    void selectUser() {
        int userId = userDAO.createUser(user1);
        User user = userDAO.getUser(userId);
        userAssertions(user1, user);
    }

    @Test
    void selectUsers() {
        userDAO.createUser(user1);
        userDAO.createUser(user2);
        List<User> users = userDAO.selectUsers();
        Assertions.assertEquals(users.size(), 2 );
        userAssertions(user1, users.get(0));
        userAssertions(user2, users.get(1));
    }

    @Test
    void getUserSearch(){
        userDAO.createUser(user1);
        userDAO.createUser(user2);
        List<User> users = userDAO.getUserSearch("Doe");
        Assertions.assertEquals(2, users.size(), "Search Doe");

        users = userDAO.getUserSearch("John");
        Assertions.assertEquals(1, users.size(), "Search John");

        users = userDAO.getUserSearch("22");
        Assertions.assertEquals(2, users.size(),  "Search 22");

        users = userDAO.getUserSearch("Will Return No Users");
        Assertions.assertEquals(0, users.size(),  "Search, will return no users");
    }

    @Test
    void login(){
        userDAO.createUser(user1);
        userDAO.createUser(user2);

        User u = userDAO.login("NoUsername", "NoPassword");
        Assertions.assertNull(u, "Should be null, non excisting username and password");

        User user = userDAO.login(user1.getUsername(), user1.getPassword());
        userAssertions(user1, user);

        u = userDAO.login(user1.getUsername(), user2.getPassword());
        Assertions.assertNull(u, "Should be null, Mix username and password of two users");
    }

    @Test
    void updateUser(){
        int userId = userDAO.createUser(user1);
        user2.setUserId(userId);
        User user = userDAO.updateUser(user2);
        userAssertions(user2, user);
    }

    @Test
    void deleteUser(){
        userDAO.createUser(user1);
        userDAO.createUser(user2);

        Assertions.assertEquals(2, userDAO.selectUsers().size());

        userDAO.deleteUser(user1.getUserId());
        Assertions.assertEquals(1, userDAO.selectUsers().size());

        userDAO.deleteUser(user2.getUserId());
        Assertions.assertEquals(0, userDAO.selectUsers().size());
    }

    @Test
    void getUSerByUsername(){
        userDAO.createUser(user1);
        userDAO.createUser(user2);

        List<User> users = userDAO.getUserByUsername(user1.getUsername());
        Assertions.assertEquals(1, users.size());
        userAssertions(user1, users.get(0));

        users = userDAO.getUserByUsername(user2.getUsername());
        Assertions.assertEquals(1, users.size());
        userAssertions(user2, users.get(0));
    }

    @Test
    void getUSerByEmail(){
        userDAO.createUser(user1);
        userDAO.createUser(user2);

        List<User> users = userDAO.getUserByEmail(user1.getEmail());
        Assertions.assertEquals(1, users.size());
        userAssertions(user1, users.get(0));

        users = userDAO.getUserByEmail(user2.getEmail());
        Assertions.assertEquals(1, users.size());
        userAssertions(user2, users.get(0));
    }
}