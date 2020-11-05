package easyon.dating.app.services;

import easyon.dating.app.data.UserRatingDAO;
import easyon.dating.app.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRatingService {

    private final UserRatingDAO userRatingDAO;

    @Autowired
    public UserRatingService(UserRatingDAO userRatingDAO) {
        this.userRatingDAO = userRatingDAO;
    }

    public void createUserRating(UserRating userRating) {
        userRatingDAO.createUserRating(userRating);}

    public List<UserRating> getEmptyUserRatingArray(int nRatings) {
        ArrayList<UserRating> emptyUserRatingArray = new ArrayList<>();
        for(int i = 0; i < nRatings; i++){
            emptyUserRatingArray.add(new UserRating());
        }
        return emptyUserRatingArray;
    }
}
