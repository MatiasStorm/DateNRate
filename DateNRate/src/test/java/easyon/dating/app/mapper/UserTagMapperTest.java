package easyon.dating.app.mapper;

import easyon.dating.app.models.UserTag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class UserTagMapperTest {

    @Mock
    private ResultSet resultSet;

    private UserTagMapper mapper = new UserTagMapper();

    private int tagId = 1;
    private int userId = 2;

    @BeforeEach
    private void setup() throws SQLException{
        Mockito.when(resultSet.getInt("tag_id")).thenReturn(tagId);
        Mockito.when(resultSet.getInt("user_id")).thenReturn(userId);
    }

    @Test
    void mapRow() throws SQLException {
        UserTag userTag = mapper.mapRow(resultSet, 1);

        Assertions.assertEquals(userTag.getTagId(), tagId);
        Assertions.assertEquals(userTag.getUserId(), userId);
    }

}