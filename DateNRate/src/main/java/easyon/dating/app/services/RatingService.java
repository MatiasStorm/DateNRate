package easyon.dating.app.services;

import easyon.dating.app.repository.RatingDAO;
import easyon.dating.app.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
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



