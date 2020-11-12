package easyon.dating.app.repository;

import easyon.dating.app.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;

@ExtendWith(SpringExtension.class)
@DataJdbcTest
class UserDAOTest {

    @Autowired
    private UserDAO userDAO;

    private User user1 = new User();
    private User user2 = new User();

    @BeforeEach
    public void setup(){
        user1.setUserId(1);
        user2.setUserId(2);

        user1.setFirstName("John");
        user2.setFirstName("Jane");

        user1.setLastName("Doe");
        user2.setLastName("Doe");

        user1.setEmail("john@gmail.com");
        user2.setEmail("jane@gmail.com");

        user1.setTownId(1);
        user2.setTownId(2);

        user1.setPassword("password");
        user2.setPassword("password");

        user1.setUsername("John22");
        user2.setUsername("Jane22");

        user1.setDateOfBirth(new Date(System.currentTimeMillis()));
        user2.setDateOfBirth(new Date(System.currentTimeMillis()));

        user1.setMale(true);
        user2.setMale(false);
    }

    @Test
    void createUser(){
        User user = userDAO.createUser(user1);
        Assertions.assertEquals(user.getUserId(), user1.getUserId());
    }

//    @Test
//    void selectUsers() {
//    }
//
//    @Test
//    void getUser() {
//    }
}