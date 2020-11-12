package easyon.dating.app.repository;

import easyon.dating.app.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJdbcTest
class UserDAOTest {

    private User user1;
    private User user2;

    @BeforeEach
    public void setup(){

    }

    @Test
    void selectUsers() {
    }

    @Test
    void getUser() {
    }
}