package easyon.dating.app.services;

import easyon.dating.app.models.Town;
import easyon.dating.app.models.User;
import easyon.dating.app.models.UserFormError;
import easyon.dating.app.repository.TownDAO;
import easyon.dating.app.repository.UserDAO;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.relational.core.sql.SQL;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {


    @Mock
    UserDAO userDAO;
    @Mock
    TownDAO townDAO;

    private UserService userService;
    private User user1 = new User();
    private User user2 = new User();

    @BeforeEach
    void setup(){
        userService = new UserService(userDAO, townDAO);
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
        town1.setPostalCode(2200);
        town1.setTownName("København N");
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
        town1.setPostalCode(4000);
        town1.setTownName("Roskilde");
        user2.setTown(town2);
    }

    @Test
    void createUser() throws SQLException {
        Mockito.when(townDAO.getTownByPostcalCode(user1.getTown().getPostalCode())).thenReturn(user1.getTown());
        Mockito.when(userDAO.createUser(user1)).thenReturn(user1.getUserId());
        Mockito.when(userDAO.getUser(user1.getUserId())).thenReturn(user1);
        User u = userService.createUser(user1);
        assertEquals(user1, u);
    }

    @Test
    void getUser() {
        Mockito.when(userDAO.getUser(user1.getUserId())).thenReturn(user1);
        User u = userService.getUser(user1.getUserId());
        assertEquals(user1, u);
    }

    @Test
    void login() {
        Mockito.when(userDAO.login(user1.getUsername(), user1.getPassword())).thenReturn(user1);
        User u = userService.login(user1.getUsername(), user1.getPassword());
        assertEquals(user1, u);
    }

    @Test
    void searchUser() {
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        Mockito.when(userDAO.getUserSearch(user1.getUsername())).thenReturn(userList);
        List<User> actualUserList = userService.searchUser(user1.getUsername());
        assertEquals(userList, actualUserList);
    }

//    private void userFromErrorMock(){
//        Mockito.when(userDAO.getUserByUsername(user1.getUsername())).thenReturn(new ArrayList() {
//            {
//                add(user1);
//            }
//        });
//        Mockito.when(userDAO.getUserByUsername(user2.getUsername())).thenReturn(new ArrayList<>(){
//            {
//                add(user2);
//            }
//        });
//        Mockito.when(userDAO.getUserByEmail(user1.getUsername())).thenReturn(new ArrayList() {
//            {
//                add(user1);
//            }
//        });
//        Mockito.when(userDAO.getUserByEmail(user2.getUsername())).thenReturn(new ArrayList<>(){
//            {
//                add(user2);
//            }
//        });
//        Mockito.when(townDAO.getTownByPostcalCode(user1.getTown().getPostalCode())).thenReturn(user1.getTown());
//        Mockito.when(townDAO.getTownByPostcalCode(user2.getTown().getPostalCode())).thenReturn(user2.getTown());
//    }

    @Test
    void getUserFormErrorPassword_newUser() {
        user1.setPassword2("");
        UserFormError userFormError = userService.getUserFormError(user1);
        assertTrue(userFormError.containsErrors());
        assertTrue(userFormError.isPasswordError());
        assertFalse(userFormError.isDateOfBirthError());
        assertFalse(userFormError.isEmailError());
        assertFalse(userFormError.isTownError());
        assertFalse(userFormError.isUsernameError());

    }
    @Test
    void getUserFormErrorUsername_newUser() {
        user1.setPassword2(user1.getPassword());
        Mockito.when(userDAO.getUserByUsername(user1.getUsername())).thenReturn(new ArrayList() {
            {
                add(user1);
            }
        });
        UserFormError userFormError = userService.getUserFormError(user1);
        assertTrue(userFormError.containsErrors());
        assertTrue(userFormError.isUsernameError());
        assertFalse(userFormError.isPasswordError());
        assertFalse(userFormError.isDateOfBirthError());
        assertFalse(userFormError.isEmailError());
        assertFalse(userFormError.isTownError());
    }

    @Test
    void getUserFormErrorEmail_newUser() {
        user1.setPassword2(user1.getPassword());
        Mockito.when(userDAO.getUserByEmail(user1.getEmail())).thenReturn(new ArrayList() {
            {
                add(user1);
            }
        });
        UserFormError userFormError = userService.getUserFormError(user1);
        assertTrue(userFormError.containsErrors());
        assertTrue(userFormError.isEmailError());
        assertFalse(userFormError.isUsernameError());
        assertFalse(userFormError.isPasswordError());
        assertFalse(userFormError.isDateOfBirthError());
        assertFalse(userFormError.isTownError());
    }

    @Test
    void getUserFormErrorTown_newUser() throws SQLException {
        user1.setPassword2(user1.getPassword());
        Mockito.when(townDAO.getTownByPostcalCode(user1.getTown().getPostalCode())).thenThrow(new SQLException());
        UserFormError userFormError = userService.getUserFormError(user1);
        assertTrue(userFormError.containsErrors());
        assertTrue(userFormError.isTownError());
        assertFalse(userFormError.isEmailError());
        assertFalse(userFormError.isUsernameError());
        assertFalse(userFormError.isPasswordError());
        assertFalse(userFormError.isDateOfBirthError());
    }

    @Test
    void testGetUserFormError() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }
}