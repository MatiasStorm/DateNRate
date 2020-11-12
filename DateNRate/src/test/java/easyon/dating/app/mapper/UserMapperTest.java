package easyon.dating.app.mapper;

import easyon.dating.app.models.Town;
import easyon.dating.app.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    @Mock
    private ResultSet resultSet;

    private UserMapper mapper = new UserMapper();

    
    private int userId = 1;
    private String firstName = "John";
    private String lastName = "Doe";
    private String password = "password123";
    private String email = "John@doe.com";
    private String userDescription = "This is a description";
    private String profilePicture = "/images/picture.png";
    private String defaultProfilePicture = "/images/mand_default.webp";
    private String username = "JohnDoe1990";
    private Date dateOfBirth = new Date(System.currentTimeMillis() );
    private Date created = new Date(System.currentTimeMillis() );
    private double rating = 5.5;
    private int amountOfRatings = 10;
    private Town town = new Town(){
        private int townId = 1;
        private String townName = "Roskilde";
        private int postalCode = 4000;
    };

    @BeforeEach
    private void setup() throws SQLException {
        Mockito.when(resultSet.getInt("user_id")).thenReturn(userId);
        Mockito.when(resultSet.getString("first_name")).thenReturn(firstName);
        Mockito.when(resultSet.getString("last_name")).thenReturn(lastName);
        Mockito.when(resultSet.getString("email")).thenReturn(email);
        Mockito.when(resultSet.getInt("town_id")).thenReturn(town.getTownId());
        Mockito.when(resultSet.getInt("postal_code")).thenReturn(town.getPostalCode());
        Mockito.when(resultSet.getString("town_name")).thenReturn(town.getTownName());
        Mockito.when(resultSet.getString("password")).thenReturn(password);
        Mockito.when(resultSet.getString("username")).thenReturn(username);
        Mockito.when(resultSet.getString("profile_picture")).thenReturn(null);
        Mockito.when(resultSet.getDate("date_of_birth")).thenReturn(dateOfBirth);
        Mockito.when(resultSet.getDate("created")).thenReturn(created);
        Mockito.when(resultSet.getString("user_description")).thenReturn(userDescription);
    }

    @Test
    void simpleUser() throws SQLException {
        User user = mapper.mapRow(resultSet, 1);
        Assertions.assertNotNull(user);
        Assertions.assertEquals(user.getUserId(), userId);
        Assertions.assertEquals(user.getFirstName(), firstName);
        Assertions.assertEquals(user.getLastName(), lastName);
        Assertions.assertEquals(user.getEmail(), email);
        Assertions.assertEquals(user.getTownId(), town.getTownId());
        Assertions.assertEquals(user.getPassword(), password);
        Assertions.assertEquals(user.getPassword2(), password);
        Assertions.assertEquals(user.getProfilePicture(), defaultProfilePicture);
        Assertions.assertEquals(user.getDateOfBirth().getTime(), dateOfBirth.getTime());
        Assertions.assertEquals(user.getUserDescription(), userDescription);
        Assertions.assertEquals(user.getTown().getTownId(), town.getTownId());
        Assertions.assertEquals(user.getTown().getTownName(), town.getTownName());
        Assertions.assertEquals(user.getTown().getPostalCode(), town.getPostalCode());
        Assertions.assertEquals(user.getAvgRating(), 0);
        Assertions.assertEquals(user.getAmountOfRatings(), 0);
    }

    @Test
    void setProfilePicture() throws SQLException {
        Mockito.when(resultSet.getString("profile_picture")).thenReturn(profilePicture);
        User user = mapper.mapRow(resultSet, 1);
        Assertions.assertEquals(user.getProfilePicture(), profilePicture);
    }

    @Test
    void setProfilePictureEmpty() throws SQLException {
        Mockito.when(resultSet.getString("profile_picture")).thenReturn("");
        User user = mapper.mapRow(resultSet, 1);
        Assertions.assertEquals(user.getProfilePicture(), defaultProfilePicture);
    }

    @Test
    void setRating() throws SQLException {
        Mockito.when(resultSet.getDouble("rating")).thenReturn(rating);
        Mockito.when(resultSet.getInt("amount_of_ratings")).thenReturn(amountOfRatings);
        User user = mapper.mapRow(resultSet, 1);
        Assertions.assertEquals(user.getAvgRating(), rating);
        Assertions.assertEquals(user.getAmountOfRatings(), amountOfRatings);
    }
}

