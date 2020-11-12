package easyon.dating.app.mapper;

import easyon.dating.app.models.UserRating;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;

@ExtendWith(MockitoExtension.class)
class UserRatingMapperTest {

    @Mock
    private ResultSet resultSet;

    private UserRatingMapper mapper = new UserRatingMapper();


    private int ratingId = 1;
    private int targetUserId = 2;
    private String ratingName = "Udseende";
    private double avgRating = 5.5;

    @BeforeEach
    private void setup() throws SQLException{
        Mockito.when(resultSet.getInt("target_user_id")).thenReturn(targetUserId);
        Mockito.when(resultSet.getInt("rating_id")).thenReturn(ratingId);
        Mockito.when(resultSet.getDouble("rating")).thenReturn(avgRating);
        Mockito.when(resultSet.getString("ratings.rating_name")).thenReturn(ratingName);

    }

    @Test
    void mapRow() throws SQLException {
        UserRating userRating = mapper.mapRow(resultSet, 1);
        Assertions.assertEquals(userRating.getUserRatingId(), ratingId);
        Assertions.assertEquals(userRating.getTargetUserId(),targetUserId);
        Assertions.assertEquals(userRating.getAvgRating(), avgRating);
        Assertions.assertEquals(userRating.getRatingName(), ratingName);

    }
}