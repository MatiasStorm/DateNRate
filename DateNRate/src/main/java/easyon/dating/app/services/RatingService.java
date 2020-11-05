package easyon.dating.app.services;

import easyon.dating.app.data.RatingDAO;
import easyon.dating.app.data.UserDAO;
import easyon.dating.app.models.Rating;
import easyon.dating.app.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {
    private final RatingDAO ratingDAO;

    @Autowired
    public RatingService(RatingDAO ratingDAO) {
        this.ratingDAO = ratingDAO;
    }

    public List<Rating> getRatings() {
        return ratingDAO.selectRatings();
    }

}



