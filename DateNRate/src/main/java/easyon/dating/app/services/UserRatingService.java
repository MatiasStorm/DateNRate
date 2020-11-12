package easyon.dating.app.services;

import easyon.dating.app.models.Rating;
import easyon.dating.app.models.UserRating;
import easyon.dating.app.repository.RatingDAO;
import easyon.dating.app.repository.UserRatingDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRatingService {

    private final UserRatingDAO userRatingDAO;
    private final RatingDAO ratingDAO;

    @Autowired
    public UserRatingService(UserRatingDAO userRatingDAO, RatingDAO ratingDAO) {
        this.userRatingDAO = userRatingDAO;
        this.ratingDAO = ratingDAO;
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

    public List<UserRating> getUserRatings(int userId) {
        return userRatingDAO.getUserRatingList(userId);
    }

    public List<Rating> getRatings() {
        return ratingDAO.selectRatings();
    }
}
